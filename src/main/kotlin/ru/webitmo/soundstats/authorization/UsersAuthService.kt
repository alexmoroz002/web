package ru.webitmo.soundstats.authorization

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.stereotype.Service
import ru.webitmo.soundstats.application.UsersService
import ru.webitmo.soundstats.authorization.entities.SpotifyAuthorizedUser

@Service
class UsersAuthService : DefaultOAuth2UserService() {
    @Autowired
    lateinit var usersService: UsersService
    override fun loadUser(userRequest : OAuth2UserRequest) : SpotifyAuthorizedUser {
        val oAuth2User = super.loadUser(userRequest)
        val authorizedUser = SpotifyAuthorizedUser(oAuth2User.attributes, oAuth2User.authorities)
        val existingUser = usersService.getUser(authorizedUser.spotifyId)
        if (existingUser != null) {
            authorizedUser.updatePrototype(existingUser)
        }
        usersService.saveUser(authorizedUser.getPrototype())
        return authorizedUser
    }
}