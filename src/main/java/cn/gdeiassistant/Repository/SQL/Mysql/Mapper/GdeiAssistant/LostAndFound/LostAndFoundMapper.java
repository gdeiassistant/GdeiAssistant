package cn.gdeiassistant.Repository.SQL.Mysql.Mapper.GdeiAssistant.LostAndFound;

import cn.gdeiassistant.Pojo.Entity.LostAndFoundInfo;
import cn.gdeiassistant.Pojo.Entity.LostAndFoundItem;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.Date;
import java.util.List;

public interface LostAndFoundMapper {

    @Select("select * from lostandfound join profile using (username) where id=#{id} limit 1")
    @Results(id = "LostAndFoundInfo", value = {
            @Result(property = "lostAndFoundItem.id", column = "id"),
            @Result(property = "lostAndFoundItem.username", column = "username"),
            @Result(property = "lostAndFoundItem.name", column = "name"),
            @Result(property = "lostAndFoundItem.description", column = "description"),
            @Result(property = "lostAndFoundItem.location", column = "location"),
            @Result(property = "lostAndFoundItem.itemType", column = "item_type"),
            @Result(property = "lostAndFoundItem.lostType", column = "lost_type"),
            @Result(property = "lostAndFoundItem.qq", column = "qq"),
            @Result(property = "lostAndFoundItem.wechat", column = "wechat"),
            @Result(property = "lostAndFoundItem.phone", column = "phone"),
            @Result(property = "lostAndFoundItem.state", column = "state"),
            @Result(property = "lostAndFoundItem.publishTime", column = "publish_time", javaType = Date.class, jdbcType = JdbcType.TIMESTAMP),
            @Result(property = "profile.username", column = "username"),
            @Result(property = "profile.nickname", column = "nickname"),
            @Result(property = "profile.birthday", column = "birthday"),
            @Result(property = "profile.degree", column = "degree"),
            @Result(property = "profile.faculty", column = "faculty"),
            @Result(property = "profile.major", column = "major"),
            @Result(property = "profile.region", column = "region"),
            @Result(property = "profile.state", column = "state"),
            @Result(property = "profile.city", column = "city"),
    })
    LostAndFoundInfo selectInfoByID(Integer id);

    @Select("select * from lostandfound where username=#{username} order by id desc")
    @Results(id = "LostAndFoundItem", value = {
            @Result(property = "id", column = "id"),
            @Result(property = "username", column = "username"),
            @Result(property = "name", column = "name"),
            @Result(property = "description", column = "description"),
            @Result(property = "location", column = "location"),
            @Result(property = "itemType", column = "item_type"),
            @Result(property = "lostType", column = "lost_type"),
            @Result(property = "state", column = "state"),
            @Result(property = "publishTime", column = "publish_time", javaType = Date.class, jdbcType = JdbcType.TIMESTAMP)
    })
    List<LostAndFoundItem> selectItemByUsername(String username);

    @Select("select * from lostandfound where lost_type=#{lostType} and state='0' order by id desc limit #{start},#{size}")
    @ResultMap("LostAndFoundItem")
    List<LostAndFoundItem> selectAvailableItem(@Param("lostType") Integer lostType
            , @Param("start") Integer start, @Param("size") Integer size);

    @Select("select * from lostandfound where lost_type=#{lostType} and item_type=#{itemType} and state='0' order by id desc limit #{start},#{size}")
    @ResultMap("LostAndFoundItem")
    List<LostAndFoundItem> selectItemByItemType(@Param("lostType") Integer lostType
            , @Param("itemType") Integer itemType, @Param("start") Integer start
            , @Param("size") Integer size);

    @Select("select distinct * from lostandfound where state='0' and lost_type=#{lostType} and (name like '%${keyword}%' or description like '%${keyword}%' " +
            "or location like '%${keyword}%') order by id desc limit #{start},#{size}")
    @ResultMap("LostAndFoundItem")
    List<LostAndFoundItem> selectItemWithKeyword(@Param("lostType") Integer lostType
            , @Param("keyword") String keyword, @Param("start") Integer start
            , @Param("size") Integer size);

    @Insert("insert into lostandfound (username,name,description,location,item_type,lost_type,qq,wechat,phone,state,publish_time) " +
            "values (#{username},#{name},#{description},#{location},#{itemType},#{lostType},#{qq},#{wechat},#{phone},'0',#{publishTime})")
    @Options(useGeneratedKeys = true)
    void insertItem(LostAndFoundItem lostAndFoundItem);

    @Update("update lostandfound set name=#{name},description=#{description},location=#{location},item_type=#{itemType},lost_type=#{lostType},qq=#{qq},wechat=#{wechat},phone=#{phone} where id=#{id}")
    void updateItemItem(LostAndFoundItem lostAndFoundItem);

    @Update("update lostandfound set state=#{state} where id=#{id}")
    void updateItemState(@Param("id") Integer id, @Param("state") Integer state);
}
