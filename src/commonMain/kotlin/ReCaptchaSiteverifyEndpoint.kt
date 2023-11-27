package net.lsafer.recaptcha.client

import io.ktor.client.call.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * [recaptcha api](https://developers.google.com/recaptcha/docs/verify#api-response)
 */
const val ReCaptchaSiteverifyEndpoint = "/recaptcha/api/siteverify"

@Serializable
data class ReCaptchaSiteverifyResponse(
    /**
     * Whether this request was a valid reCAPTCHA token for your site
     */
    @SerialName("success")
    val success: Boolean,
    /**
     * The score for this request (0.0 - 1.0)
     */
    @SerialName("score")
    val score: Float? = null,
    /**
     * The action name for this request (important to verify)
     */
    @SerialName("action")
    val action: String? = null,
    /**
     * Timestamp of the challenge load (ISO format yyyy-MM-dd'T'HH:mm:ssZZ)
     */
    @SerialName("challenge_ts")
    val challengeTimestamp: String? = null,
    /**
     * The hostname of the site where the reCAPTCHA was solved
     */
    val hostname: String? = null,
    @SerialName("error-codes")
    val errorCodes: List<String> = emptyList(),
)

/**
 * @param secret the shared key between your site and reCAPTCHA.
 * @param response the user response token provided by the reCAPTCHA client-side integration on your site.
 * @param remoteip the user's IP address.
 */
suspend fun ReCaptchaAPI.siteverify(
    secret: String,
    response: String,
    remoteip: String? = null,
): ReCaptchaSiteverifyResponse {
    // https://developers.google.com/recaptcha/docs/verify#api_request
    return client.submitForm(
        url = ReCaptchaSiteverifyEndpoint,
        formParameters = parameters {
            set("secret", secret)
            set("response", response)
            if (remoteip != null) {
                set("remoteip", remoteip)
            }
        }
    ).body()
}
