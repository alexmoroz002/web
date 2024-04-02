package ru.webitmo.soundstats.authorization

import io.swagger.v3.oas.annotations.Hidden
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets
import java.util.stream.Collectors

// ОЧЕНЬ плохой хак для ОЧЕНЬ плохой "фичи"
// https://github.com/swagger-api/swagger-ui/commit/937c8f6208f3adf713b10a349a82a1b129bd0ffd

@RestController
@Hidden
@RequestMapping(path = ["/swagger-ui"])
class SwaggerFixController {
    @GetMapping(path = ["/index.html"])
    fun addJsFix(): String {
        val orig = toText(javaClass.getResourceAsStream("/META-INF/resources/webjars/swagger-ui/5.11.8/index.html"))
        return "$orig\n\n<script charset=\"UTF-8\"> ${script()} </script>"
    }

    private fun toText(`in`: InputStream?): String {
        if (`in` != null) {
            return BufferedReader(InputStreamReader(`in`, StandardCharsets.UTF_8))
                .lines().collect(Collectors.joining("\n"))
        }
        return ""
    }

    private fun script() : String {
        return "window.fetch = function (fetch) {\n" +
                "    return function () {\n" +
                "        var req = arguments[1];\n" +
                "        if(req.headers[\"X-Requested-With\"]) {\n" +
                "            delete req.headers[\"X-Requested-With\"];\n" +
                "        }\n" +
                "        return fetch.apply(window, arguments);\n" +
                "    };\n" +
                "}(window.fetch);"
    }
}