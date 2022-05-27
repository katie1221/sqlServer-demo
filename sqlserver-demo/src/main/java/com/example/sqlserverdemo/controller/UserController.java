package com.example.sqlserverdemo.controller;

import com.example.sqlserverdemo.dao.UserDao;
import com.example.sqlserverdemo.entity.User;
import com.example.sqlserverdemo.utils.Result;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;
import java.util.Date;
import java.util.List;

/**
 * @author qzz
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserDao userDao;

    /**
     * 新增
     * @param user
     * @return
     */
    @PostMapping("/add")
    public Result add(@RequestBody User user){
        user.setCreateUser("admin");
        user.setCreateTime(new Date());
        //insertSelective 保存一个实体，null的属性不会保存，会使用数据库默认值
       int i= userDao.insertSelective(user);
       if(i>0){
           return new Result().ok("新增成功");
       }else{
           return new Result().error("新增失败");
       }
    }

    /**
     * 编辑
     * @param user
     * @return
     */
    @PostMapping("/update")
    public Result update(@RequestBody User user){
        user.setUpdateUser("admin");
        user.setUpdateTime(new Date());
        //根据主键id更新用户信息  updateByPrimaryKeySelective:根据主键更新属性不为null的值
       int i= userDao.updateByPrimaryKeySelective(user);
       if(i>0){
           return new Result().ok("更新成功");
       }else{
           return new Result().error("更新失败");
       }
    }

    /**
     * 查看用户详情
     * @return
     */
    @GetMapping("/userInfo")
    public Result getUserById(@RequestParam("userId") Long id){
        User user = new User();
        user.setId(id);
        User userInfo = userDao.selectOne(user);
        return new Result().ok(userInfo);
    }

    /**
     * 查看所有
     * @return
     */
    @GetMapping("/list")
    public Result getList(){
        List<User> list = userDao.selectAll();
        return new Result().ok(list);
    }

    /**
     * 根据条件查看符合条件的列表
     * @return
     */
    @GetMapping("/getListByCondition")
    public Result getListByCondition(@RequestParam("name") String name,@RequestParam("age") Integer age){
        //根据名称获取用户信息

        //通用的Example查询对象
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andLike("name","%"+name+"%");
        criteria.andEqualTo("age",age);

        List<User> list = userDao.selectByExample(example);
        return new Result().ok(list);
    }

    /**
     * 分页查看用户列表
     */
    @GetMapping("/page")
    public PageInfo<User> page(@RequestParam("page") Integer page, @RequestParam("pageSize") Integer pageSize){
        PageHelper.startPage(page,pageSize);
        List<User> list = userDao.selectAll();
        return new PageInfo<>(list);
    }
}
