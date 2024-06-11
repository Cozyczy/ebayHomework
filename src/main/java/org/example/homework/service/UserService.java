package org.example.homework.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.homework.entity.UserEndpoint;
import org.example.homework.entity.UserEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private static final String DATA_PATH = "src/main/resources/data.json";
    private ObjectMapper mapper = new ObjectMapper();

    //通过userId查询，该user是否已经存在
    public UserEndpoint getUserAccessByUserEntity(Integer userId) throws Exception {
        List<UserEndpoint> userEndpointList = getAllAccess();
        for (UserEndpoint userEndpoint : userEndpointList){
            //如果该user已经存在，则返回该userId 和 resource信息（userEndpoint），没有则返回空
            if (userEndpoint.getUserId() != null && !"".equals(userEndpoint.getUserId())
                && userId != null && userId.equals(userEndpoint.getUserId())){
                return userEndpoint;
            }
        }
        return null;
    }

    //获取json文件中所有的user的resource信息
    public List<UserEndpoint> getAllAccess() throws Exception {
        File file = new File(DATA_PATH);
        return mapper.readValue(file, new TypeReference<List<UserEndpoint>>(){});
    }

    //将请求中的user的resource信息信息存到json文件中
    public void saveAccess(UserEntity userEntity,String resource) throws Exception {
        List<UserEndpoint> userEndpointList = getAllAccess();
        //如果json文件中存在该user直接新增resource；不存user在直接将输入的userEndpoint保存到json文件
        //todo 测试能不能直接在list里面判断某一个属性是否包含
        List<Integer> useIdList = new ArrayList<>();
        for (UserEndpoint userEndpoint : userEndpointList){
            useIdList.add(userEndpoint.getUserId());
        }
        if (useIdList.contains(userEntity.getUserId())){
            for (UserEndpoint userEndpoint : userEndpointList){
                //如果该user已经存在，则返回该userId 和 resource信息（userEndpoint)
                //且不存json文件中中判断新增的resource是否已经存在，不存在则添加
                if (userEndpoint.getUserId() != null && !"".equals(userEndpoint.getUserId())
                        && userEntity.getUserId() != null
                        && userEntity.getUserId().equals(userEndpoint.getUserId())
                        && !userEndpoint.getEndpoints().contains(resource)){
                    userEndpoint.getEndpoints().add(resource);
                }
            }
        }else {
            UserEndpoint userEndpointSave = new UserEndpoint();
            userEndpointSave.setUserId(userEntity.getUserId());
            List<String> endpoints = new ArrayList<>();
            endpoints.add(resource);
            userEndpointSave.setEndpoints(endpoints);
            userEndpointList.add(userEndpointSave);
        }

        mapper.writeValue(new File(DATA_PATH), userEndpointList);
    }

}
