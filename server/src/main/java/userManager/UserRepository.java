package userManager;


import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    /**
     * Get user id by given email.
     *
     * @param email the email to be queried
     * @return the id of the user if the id is not null, otherwise null
     */
    @Query(value = "SELECT id FROM user_info WHERE email = :userEmail", nativeQuery = true)
    Long getUserIdByEmail(@Param("userEmail") String email);

    /**
     * Update user ip and port.
     *
     * @param id the user id
     * @param hostname hostname
     * @param port port
     */
    @Modifying
    @Query(value = "UPDATE user_info SET hostname = :userHostname, port = :userPort WHERE id = :userId", nativeQuery = true)
    void updateHostnameAndPort(@Param("userId") Long id, @Param("userHostname") String hostname, @Param("userPort") int port);

    /**
     * Update the status that user login and logout.
     *
     * @param id the user id
     * @param isLogin the login status
     */
    @Modifying
    @Query(value = "UPDATE user_info SET is_login = :userLogin WHERE id = :userId", nativeQuery = true)
    void updateLogin(@Param("userId") Long id, @Param("userLogin") boolean isLogin);
}
