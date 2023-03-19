package cn.ohbug.authcenter.dao;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

/**
 * 主要用于逻辑删除的实现，通过重写jpa中的方法，修改实体类的isdel属性 进行逻辑删除和物理删除的实现
 *
 * @param <T> 实体类型
 *            <Integer> 实体主键类型
 */
@NoRepositoryBean
public interface BaseDao<T> extends JpaRepository<T, Integer> {

  /**
   * Transactional(rollbackFor=Exception.class)，方法抛出异常，就会回滚，数据库里面的数据也会回滚。
   * 如果不配置rollbackFor属性,那么事物只会在遇到 RuntimeException 的时候才会回滚,
   * 加上rollbackFor=Exception.class,可以让事物在遇到非运行时异常时也回滚
   *
   * @param id 用户id
   */
  @Modifying
  @Query(value = "UPDATE #{#entityName} SET isdel = 1 WHERE id = ?1", nativeQuery = true)
  @Transactional(rollbackFor = Exception.class)
  int logicDeleteById(Long id);

  /**
   * 逻辑删除， system_user表中字段 isdel=1时，则查不出来这个用户
   *
   * @param id must not be {@literal null}.  用户id
   * @return 用户对象
   * <p>
   * #{#entityName}会取@Entity()的值，默认是类名小写
   */
  @Query(value = "SELECT * FROM #{#entityName} WHERE id = ?1 AND isdel = 0", nativeQuery = true)
  @Transactional(readOnly = true)
  // 优化查询速度
  Optional<T> findById(Long id);


  @Override
  @Query(value = "SELECT * FROM #{#entityName}  WHERE isdel = 0", nativeQuery = true)
  List<T> findAll();


}
