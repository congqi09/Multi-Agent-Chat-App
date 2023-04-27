//package userManager;
//
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//
//import javax.transaction.Transactional;
//import java.net.InetAddress;
//import java.net.UnknownHostException;
//import java.util.HashMap;
//import java.util.Map;
//
//@Slf4j
//@Service
//public class UserService {
//
//    private final UserRepository userRepository;
//
//    @Autowired
//    public UserService(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//    @Transactional
//    public ResponseEntity<String> signup(Map<String, String> data) {
//        // parse the map
//        // get email
//        String email = data.get("email");
//        // get password
//        String password = data.get("password");
//        // get nickname
//        String nickname = data.get("username");
//
//        // look up for the email if it is already registered
//        Long id = userRepository.getUserIdByEmail(email);
//        if (id != null) {
//            return ResponseEntity.badRequest().body("The email has been registered");
//        }
//        // accept the registry
//        // generate salt for the user
//        String salt = Encryption.saltGenerater();
//        // encrypt the password
//        String encryptedPassword = Encryption.md5(password, salt);
//        // create an account
//        User user = new User(nickname, email, encryptedPassword, salt);
//        // save the com.neu.chatApp.server.entity
//        userRepository.save(user);
//        return ResponseEntity.ok("Welcome to join us");
//    }
//
//    @Transactional
//    public ResponseEntity<Map<String, Object>> login(Map<String, Object> data) {
//        // parse map
//        String email = (String) data.get("email");
//        String password = (String) data.get("password");
//        String hostname = (String) data.get("hostname");
//        int port = (int) data.get("port");
//
//        Map<String, Object> response = new HashMap<>();
//        // check if the user exists
//        Long id = userRepository.getUserIdByEmail(email);
//        if (id == null) {
//            response.put("error", "Account doesn't exist");
//            return ResponseEntity.badRequest().body(response);
//        }
//        // check password
//        User user = userRepository.findById(id).get();
//        String salt = user.getSalt();
//        String givenPassword = Encryption.md5(password, salt);
//        String correctPassword = user.getPassword();
//        // incorrect password
//        if (!correctPassword.equals(givenPassword)) {
//            response.put("error", "Incorrect password");
//            return ResponseEntity.badRequest().body(response);
//        }
//
//        // check if the user has already login somewhere
//        // abort the request
//        if (user.isLogin()) {
//            response.put("error", "Your account has logged in at somewhere");
//            return ResponseEntity.badRequest().body(response);
//        }
//
//        // record the ip and port of this time login
//        // com.neu.chatApp.server.db
//        userRepository.updateLogin(user.getId(), true);
//        userRepository.updateHostnameAndPort(id, hostname, port);
//
//        // return the leader hostname and port of the p to p network
//        // and the id the user
//        response.put("id", id);
//        response.put("nickname", user.getNickname());
//        // if empty list, then the node will be assigned as leader
//        if (SharableResource.liveNodeList.size() == 0) {
//            // given the server hostname and port
//            try {
//                response.put("hostname", InetAddress.getLocalHost().getHostAddress());
//                response.put("port", SharableResource.myPort);
//            } catch (UnknownHostException ignored) {}
//        } else {
//            // return the leader hostname and port
//            Node leaderNode = SharableResource.liveNodeList.getLeaderNode();
//            response.put("hostname", leaderNode.getHostname());
//            response.put("port", leaderNode.getPort());
//        }
//        return ResponseEntity.ok(response);
//    }
//
//    @Transactional
//    public void logout(Long userId) {
//        if (SharableResource.liveNodeList.isContain(userId)) {
//            SharableResource.liveNodeList.remove(userId);
//            log.info("A node left id: " + userId);
//        }
//        userRepository.updateLogin(userId, false);
//    }
//}
