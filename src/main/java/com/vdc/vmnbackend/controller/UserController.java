/**
 * Controller class for managing user-related operations.
 */
@RestController
@RequestMapping("user")
public class UserController {

    private final UserService userService;

    /**
     * Constructor for UserController class.
     *
     * @param userService The UserService for managing user-related operations.
     */
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Endpoint for retrieving the profile of the logged-in user.
     *
     * @param authentication The authentication object representing the logged-in user.
     * @return A ResponseDTO containing the profile data of the user.
     */
    @GetMapping
    public ResponseDTO<ProfileDataResDTO<?>> getProfile(Authentication authentication) {
        ProfileDataResDTO<?> payload = userService.getProfile(userService.getByEmail(authentication.getName()));
        return new ResponseDTO<>(payload, new BasicResDTO(CommonConstants.PROFILE_FETCHED, HttpStatus.OK));
    }

    /**
     * Endpoint for updating the profile of the logged-in user.
     *
     * @param authentication   The authentication object representing the logged-in user.
     * @param profileDataResDTO The ProfileDataResDTO containing the updated profile data.
     * @return A BasicResDTO indicating the result of the profile update operation.
     */
    @PutMapping
    public BasicResDTO updateProfile(Authentication authentication, @RequestBody ProfileDataResDTO<?> profileDataResDTO) {
        return userService.updateProfile(userService.getByEmail(authentication.getName()), profileDataResDTO);
    }
}
