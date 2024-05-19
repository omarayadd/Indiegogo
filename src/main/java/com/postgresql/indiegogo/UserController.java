package com.postgresql.indiegogo;

import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.security.Principal;


@RestController
public class UserController {

    private static final String SECRET_KEY = "yourSecretKeykosmak3sg4gy4gggg66666gggsyys5yh5hyhg5h5h5yourSecretyourSecretKeykosmak3sg4gy4gggg66666gggsyys5yh5hyhg5h5h5yourSecret"; // Change this to a secure, random string


    @Autowired
    private UserRepo repo;
    
    @Autowired
    private MyService myService; // Inject MyService here

    @DeleteMapping("/deleteAllUsers")
    public ApiResponse deleteAllUsers() {
    	repo.deleteAll();
        System.out.println("Success: All users deleted.");
        return new ApiResponse("All users deleted successfully", null);
    }
    
    @PostMapping("/signUp")
    public ApiResponse addUser(@RequestBody Map<String, String> signUpRequest) {
        String[] requiredFields = { "email", "passwordHash", "firstName", "lastName" };

        Set<String> unauthorizedKeys = signUpRequest.keySet();
        boolean flag = true;

        for (String field : requiredFields) {
            if (!unauthorizedKeys.contains(field)) {
                flag = false;
                break;
            }
        }

        if (unauthorizedKeys.size() > 4) {
            System.out.println("Error: Only the required fields (email, passwordHash, firstName, lastName) are allowed.");
            return new ApiResponse("Only the required fields (email, passwordHash, firstName, lastName) are allowed.", null);
        }

        String email = signUpRequest.get("email");
        String passwordHash = signUpRequest.get("passwordHash");
        String firstName = signUpRequest.get("firstName");
        String lastName = signUpRequest.get("lastName");

        if (!flag || email == null || passwordHash == null || firstName == null || lastName == null) {
            System.out.println("Error: All required fields (email, passwordHash, firstName, lastName) must be provided.");
            return new ApiResponse("All required fields (email, passwordHash, firstName, lastName) must be provided.", signUpRequest);
        }

        /*if (repo.findByEmail(email) != null) {
            System.out.println("Error: User with email '" + email + "' already exists.");
            return new ApiResponse("User with email '" + email + "' already exists.", email);
        }*/

        User user = new User(email, passwordHash, firstName, lastName);
        repo.save(user);
        System.out.println("Success: User added successfully.");
        return new ApiResponse("User added successfully.", user);
    }

    @GetMapping("/getAllUsers")
    public ApiResponse getAllUsers() {
        System.out.println("Success: Retrieving all users.");
        return new ApiResponse("Users retrieved successfully", repo.findAll());
    }
    
    
    @PostMapping("/signIn")
    public ApiResponse signIn(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String password = credentials.get("passwordHash");
        User user = repo.findByEmail(email);

        if (user != null && user.getPasswordHash().equals(password)) {
            String token = generateToken(email);
            String key = email;
            String value = token;

            myService.setValue(key, value);
            
            String cachedValue = myService.getValue(key);
            
            System.out.println("Success: Generated Token:" + token);
            System.out.println("Success: Cached Token:" + cachedValue);

            return new ApiResponse("Sign-in successful", "User: " + user + " Token: " + token);
        } else {
            System.out.println("Error: Invalid email or password");
            return new ApiResponse("Invalid email or password", null);
        }
    }



    @SuppressWarnings("deprecation")
    private String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    
    
    @PostMapping("/updateInfo/{email}")
    public ApiResponse updateInfo(@PathVariable String email, @RequestBody Map<String, String> updateRequest, Principal principal) {
//        // Retrieve the email address of the signed-in user
//        String signedInEmail = principal.getName();
//
//        // Check if the signed-in user is authorized to update the information
//        if (!signedInEmail.equals(email)) {
//            return new ApiResponse("Unauthorized: You are not allowed to update another user's information.", null);
//        }
        User user = repo.findByEmail(email);
        
        // Check if the user exists
        if (user == null) {
            System.out.println("Error: User with email '" + email + "' not found.");
            return new ApiResponse("User with email '" + email + "' not found.", null);
        }
        
        // Update user information
        if (updateRequest.containsKey("passwordHash")) {
            user.setPasswordHash(updateRequest.get("passwordHash"));
        }
        
        if (updateRequest.containsKey("firstName")) {
            user.setFirstName(updateRequest.get("firstName"));
        }
        
        if (updateRequest.containsKey("lastName")) {
            user.setLastName(updateRequest.get("lastName"));
        }
        
        if (updateRequest.containsKey("country")) {
            user.setCountry(updateRequest.get("country"));
        }
        
        if (updateRequest.containsKey("city")) {
            user.setCity(updateRequest.get("city"));
        }
        
        if (updateRequest.containsKey("postalCode")) {
            user.setPostalCode(updateRequest.get("postalCode"));
        }
        
        if (updateRequest.containsKey("bio")) {
            user.setBio(updateRequest.get("bio"));
        }
        
        if (updateRequest.containsKey("profilePictureUrl")) {
            user.setProfilePictureUrl(updateRequest.get("profilePictureUrl"));
        }
        
        if (updateRequest.containsKey("facebookLink")) {
            user.setFacebookLink(updateRequest.get("facebookLink"));
        }
        
        if (updateRequest.containsKey("twitterLink")) {
            user.setTwitterLink(updateRequest.get("twitterLink"));
        }
        
        if (updateRequest.containsKey("youtubeLink")) {
            user.setYoutubeLink(updateRequest.get("youtubeLink"));
        }
        
        if (updateRequest.containsKey("imdbLink")) {
            user.setImdbLink(updateRequest.get("imdbLink"));
        }
        
        if (updateRequest.containsKey("websiteLink")) {
            user.setWebsiteLink(updateRequest.get("websiteLink"));
        }
        
        // Save the updated user
        repo.save(user);
        
        System.out.println("Success: User information updated successfully for email: " + email);
        return new ApiResponse("User information updated successfully.", user);
    }
    
    @GetMapping("/cache")
    public Map<String, String> viewCache() {
        System.out.println("Success: Viewing cache.");
        // Retrieve all keys and their corresponding values from the cache
        return myService.getAllData();
    }
    
    @PostMapping("/logOut/{email}")
    public ApiResponse logOut(@PathVariable String email) {
        if(!(myService.getValue(email) == null)) {
            myService.removeValue(email);
            System.out.println("Success: User logged out successfully for email: " + email);
            return new ApiResponse("LogOut Successfull", myService.getValue(email));
        } else {
            System.out.println("Error: User is not logged in for email: " + email);
            return new ApiResponse("User is not logged in", myService.getValue(email));
        }

    }
    
}
