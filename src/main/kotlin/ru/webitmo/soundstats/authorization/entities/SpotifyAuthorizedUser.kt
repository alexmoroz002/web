package ru.webitmo.soundstats.authorization.entities

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.oauth2.core.user.OAuth2User
import java.io.Serializable

class SpotifyAuthorizedUser(
    @get:JvmName("getAttributesMap") val attributes : MutableMap<String, Any>,
    @get:JvmName("getAuthoritiesSet") val authorities : MutableCollection<out GrantedAuthority>
) : User(
    spotifyId = attributes["id"] as String,
    country = attributes["country"] as String,
    isExplicitFiltered = (attributes["explicit_content"] as Map<*, *>)["filter_enabled"] as Boolean,
    userName = attributes["display_name"] as String?,
    avatarUrl = (((attributes["images"] as ArrayList<*>).last()) as Map<*,*>)["url"] as String,
    product = attributes["product"] as String?
), OAuth2User, Serializable {
    override fun getName(): String {
        return attributes["id"] as String
    }

    override fun getAttributes(): MutableMap<String, Any> {
        return attributes
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return authorities
    }
}