package com.eladmin.modules.system.rest;

import com.eladmin.aop.log.Log;
import com.eladmin.config.DataScope;
import com.eladmin.domain.Picture;
import com.eladmin.domain.VerificationCode;
import com.eladmin.exception.BadRequestException;
import com.eladmin.modules.system.domain.User;
import com.eladmin.modules.system.service.DeptService;
import com.eladmin.modules.system.service.UserService;
import com.eladmin.modules.system.service.dto.UserDTO;
import com.eladmin.modules.system.service.query.UserQueryService;
import com.eladmin.service.PictureService;
import com.eladmin.service.VerificationCodeService;
import com.eladmin.utils.ElAdminConstant;
import com.eladmin.utils.EncryptUtils;
import com.eladmin.utils.PageUtil;
import com.eladmin.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author tq
 * @date 2018-11-23
 */
@RestController
@RequestMapping("api")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserQueryService userQueryService;

    @Autowired
    private PictureService pictureService;

    @Autowired
    private DataScope dataScope;

    @Autowired
    private DeptService deptService;

    @Autowired
    private VerificationCodeService verificationCodeService;


    private static final String ENTITY_NAME = "user";

    @Log("查询用户")
    @GetMapping(value = "/users")
    @PreAuthorize("hasAnyRole('ADMIN','USER_ALL','USER_SELECT')")
    public ResponseEntity getUsers(UserDTO userDTO, Pageable pageable){
        Set<Long> deptSet = new HashSet<>();
        Set<Long> result = new HashSet<>();

        if (!ObjectUtils.isEmpty(userDTO.getDeptId())) {
            deptSet.add(userDTO.getDeptId());
            deptSet.addAll(dataScope.getDeptChildren(deptService.findByPid(userDTO.getDeptId())));
        }

        // 数据权限
        Set<Long> deptIds = dataScope.getDeptIds();

        // 查询条件不为空并且数据权限不为空则取交集
        if (!CollectionUtils.isEmpty(deptIds) && !CollectionUtils.isEmpty(deptSet)){

            // 取交集
            result.addAll(deptSet);
            result.retainAll(deptIds);

            // 若无交集，则代表无数据权限
            if(result.size() == 0){
                return new ResponseEntity(PageUtil.toPage(null,0),HttpStatus.OK);
            } else return new ResponseEntity(userQueryService.queryAll(userDTO,result,pageable),HttpStatus.OK);
        // 否则取并集
        } else {
            result.addAll(deptSet);
            result.addAll(deptIds);
            return new ResponseEntity(userQueryService.queryAll(userDTO,result,pageable),HttpStatus.OK);
        }
    }

    @Log("新增用户")
    @PostMapping(value = "/users")
    @PreAuthorize("hasAnyRole('ADMIN','USER_ALL','USER_CREATE')")
    public ResponseEntity create(@Validated @RequestBody User resources){
        if (resources.getId() != null) {
            throw new BadRequestException("A new "+ ENTITY_NAME +" cannot already have an ID");
        }
        return new ResponseEntity(userService.create(resources),HttpStatus.CREATED);
    }

    @Log("修改用户")
    @PutMapping(value = "/users")
    @PreAuthorize("hasAnyRole('ADMIN','USER_ALL','USER_EDIT')")
    public ResponseEntity update(@Validated(User.Update.class) @RequestBody User resources){
        userService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除用户")
    @DeleteMapping(value = "/users/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER_ALL','USER_DELETE')")
    public ResponseEntity delete(@PathVariable Long id){
        userService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 验证密码
     * @param pass
     * @return
     */
    @GetMapping(value = "/users/validPass/{pass}")
    public ResponseEntity validPass(@PathVariable String pass){
        UserDetails userDetails = SecurityUtils.getUserDetails();
        Map map = new HashMap();
        map.put("status",200);
        if(!userDetails.getPassword().equals(EncryptUtils.encryptPassword(pass))){
           map.put("status",400);
        }
        return new ResponseEntity(map,HttpStatus.OK);
    }

    /**
     * 修改密码
     * @param pass
     * @return
     */
    @GetMapping(value = "/users/updatePass/{pass}")
    public ResponseEntity updatePass(@PathVariable String pass){
        UserDetails userDetails = SecurityUtils.getUserDetails();
        if(userDetails.getPassword().equals(EncryptUtils.encryptPassword(pass))){
            throw new BadRequestException("新密码不能与旧密码相同");
        }
        userService.updatePass(userDetails.getUsername(),EncryptUtils.encryptPassword(pass));
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 修改头像
     * @param file
     * @return
     */
    @PostMapping(value = "/users/updateAvatar")
    public ResponseEntity updateAvatar(@RequestParam MultipartFile file){
        Picture picture = pictureService.upload(file, SecurityUtils.getUsername());
        userService.updateAvatar(SecurityUtils.getUsername(),picture.getUrl());
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 修改邮箱
     * @param user
     * @param user
     * @return
     */
    @Log("修改邮箱")
    @PostMapping(value = "/users/updateEmail/{code}")
    public ResponseEntity updateEmail(@PathVariable String code,@RequestBody User user){
        UserDetails userDetails = SecurityUtils.getUserDetails();
        if(!userDetails.getPassword().equals(EncryptUtils.encryptPassword(user.getPassword()))){
            throw new BadRequestException("密码错误");
        }
        VerificationCode verificationCode = new VerificationCode(code, ElAdminConstant.RESET_MAIL,"email",user.getEmail());
        verificationCodeService.validated(verificationCode);
        userService.updateEmail(userDetails.getUsername(),user.getEmail());
        return new ResponseEntity(HttpStatus.OK);
    }
}
