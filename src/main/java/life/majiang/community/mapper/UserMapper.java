package life.majiang.community.mapper;

import model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Insert("insert into USER (ID, ACCOUNT_ID, NAME,TOKEN, GMT_CREATE, GMT_MODIFY)\n" +
            "        values (#{id,jdbcType=BIGINT}, #{accountId,jdbcType=VARCHAR}, #{name,jdbcType=VARCHAR},\n" +
            "        #{token,jdbcType=VARCHAR}, #{gmtCreate,jdbcType=BIGINT}, #{gmtModify,jdbcType=BIGINT})")
    void insert(User user);

    @Select("selecr * from user where token=#{token}")
    User findByToken(@Param("token") String token);

    /*
    *  insert into USER (ID, ACCOUNT_ID, NAME,TOKEN, GMT_CREATE, GMT_MODIFIED,BIO, AVATAR_URL)
        values (#{id,jdbcType=BIGINT}, #{accountId,jdbcType=VARCHAR}, #{name,jdbcType=VARCHAR},
        #{token,jdbcType=VARCHAR}, #{gmtCreate,jdbcType=BIGINT}, #{gmtModified,jdbcType=BIGINT},
        #{bio,jdbcType=VARCHAR}, #{avatarUrl,jdbcType=VARCHAR})
    * */

}