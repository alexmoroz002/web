package ru.webitmo.soundstats.authorization

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.stereotype.Service
import ru.webitmo.soundstats.application.UsersService
import ru.webitmo.soundstats.authorization.entities.SpotifyAuthorizedUser
import ru.webitmo.soundstats.authorization.entities.User

@Service
class UsersAuthService : DefaultOAuth2UserService() {
    @Autowired
    lateinit var usersService: UsersService
    override fun loadUser(userRequest : OAuth2UserRequest) : SpotifyAuthorizedUser {
        val oAuth2User = super.loadUser(userRequest)
        val authorizedUser = SpotifyAuthorizedUser(oAuth2User.attributes, oAuth2User.authorities)
        if (usersService.getUser(oAuth2User.name) == null) {
            val user = User(
                spotifyId = oAuth2User.attributes["id"] as String,
                country = oAuth2User.attributes["country"] as String,
                isExplicitFiltered = (oAuth2User.attributes["explicit_content"] as Map<*, *>)["filter_enabled"] as Boolean,
                userName = oAuth2User.attributes["display_name"] as String?,
                avatarUrl = (((oAuth2User.attributes["images"] as ArrayList<*>).last()) as Map<*,*>)["url"] as String,
                product = oAuth2User.attributes["product"] as String?
            )
            usersService.saveUser(user)
        }
        return authorizedUser
    }
}