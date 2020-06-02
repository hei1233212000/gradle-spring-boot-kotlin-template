package poc.test.acceptance.step

import io.cucumber.java8.En
import org.amshove.kluent.`should be equal to`
import poc.test.acceptance.service.TestProtectedEndpointsService

@Suppress("unused")
class ProtectedEndpointStepDefinitions(
    private val protectedEndpointsService: TestProtectedEndpointsService
) : En {
    private var jwt: String? = null
    private lateinit var response: TestProtectedEndpointsService.ProtectedResourceResponse

    init {
        Given("I am a {userScope} user") { jwt: String? ->
            this.jwt = jwt
        }

        When("I try to access the protected endpoint for {string}") { protectedEndpoint: String ->
            val path = when (protectedEndpoint) {
                "reader" -> "/reader"
                "writer" -> "/writer"
                else -> "/"
            }
            response = protectedEndpointsService.findProtectedResource(jwt, path)
        }

        Then("I should receive status code {int}") { expectedStatusCode: Int ->
            response.status `should be equal to` expectedStatusCode
        }

        Then("I should receive {string}") { expectedResponseBody: String ->
            response.body `should be equal to` expectedResponseBody
        }

        ParameterType("userScope", "anonymous|normal|reader|writer|admin") { userScope ->
            when (userScope) {
                "admin" -> JWT_ADMIN
                "reader" -> JWT_READER
                "writer" -> JWT_WRITER
                "normal" -> JWT_NO_SCOPE
                else -> null
            }
        }
    }

    companion object {
        private const val JWT_NO_SCOPE =
            "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJqdGkiOiI2MzNjM2RmNC1jZGM5LTRkNDYtYjdmZi0wMWIyNTA5MTVmODAiLCJpYXQiOjE1OTExMDg1OTMsInN1YiI6ImFueSBzdWJqZWN0Iiwic2NvcGUiOiIifQ.dV6C1B9oq1IEzbRDaNmq05ezOLwFRnkw0s2ZbK3IbZ4Ar89-LMOKIbsiX1CEpem4w6wyHUL9hPEHf-xmp7U77N8_B2se1WpbzV_ukdZZAG2tP0NmNDln3FCFH8VxvslyAWdNbcjilcd54unp6gHtKdTx-vWGG4V7r8CQL26jy0PMZPVjj07S1E7Ozqvm-Zg3yejjgMdUVlyZjZ0ztfhomUb_DNUmILSDzbMihXD5ioNwo-d8vQECl_lCLEyAX0cuXHHwIndI9e9AhaPOVi2Qf4LDyhMsB8HxntlACdUbqYeCywt-YUgefF5XzUUj7E54Nkm2DUIgaIoMP0MqtZyjmw"
        private const val JWT_READER =
            "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJqdGkiOiJmZDk0NDg5ZC1lOWY3LTQ1Y2QtOWMzYS0zMmQ5YjZjODkyZWQiLCJpYXQiOjE1OTExMDg1OTQsInN1YiI6ImFueSBzdWJqZWN0Iiwic2NvcGUiOiJwcm90ZWN0ZWQ6cmVhZCJ9.HAdPPdnld0Hf_3dwxFkJk9sGkXTYzZKjHguoQbpNsXvNWISPoR6WRDSYevC6aTzesnVCfAYbkqUB7PwrYA7dO2gXgl-n2ZYzsvbqADiSyllvxWw_XNE6ci0KfoiR8-UA1lYOl25mGFk02rOcQUo7xttJemz1CmT5tTEng31E3BljEZbdbhi5s7x1Kz4gCTV3wrI201MBK5TdeFkMCUkL_D-Bdxgw_cTLEVLSHdh4pYTgsyCaevitKLpWPpM1l47izJ3_1Jpb1vC_X6lreUu5j_OtuTaMxHzsWJvtIUB7OwFw9OCnGR--bYQ0cHRAE2LTuEjJ6c8bLoxA2gcRjUeKtQ"
        private const val JWT_WRITER =
            "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJqdGkiOiI2MWVmOTExMS01MmMyLTQ1NjItOWRmYy1kYWEzYjRiODY1MjYiLCJpYXQiOjE1OTExMDg1OTQsInN1YiI6ImFueSBzdWJqZWN0Iiwic2NvcGUiOiJwcm90ZWN0ZWQ6d3JpdGUifQ.hPve1w9ZWJi7yAc5XIySgRogorq2H4k9CmjAgmvEUakNNvE70zfTZ66PrZQOKup1XEEh1Il1Qs4Q5LE4tueVG1btwR6Ure9Ya3CLgue0NjJoUYrlQ2HxgaUQ_vsuKrKbs1SUu1K2V8a4_GCfhc8yPw1m6-lGN7LdjeuqXJu1-Jx29DP22a-yw5NT-NRQPnWpzy2u3TZdP9vdsmGxlDT4eZykPLFcFb7EFTo7ca7LTFa2kffdTo0Gr7onXI71kKu6xKfw0w-m44Ln5Hh2OU2inSexSm7DXErf2BSnz9g8iLK-VzrPjukno3k5z07kJ1rlS4M00ZX4n6R8uaajvRf9LA"
        private const val JWT_ADMIN =
            "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJqdGkiOiIyZDNlYzc4YS03YzA4LTRmMDctYWI3Ni0yZDIzMzg0NjM4YzgiLCJpYXQiOjE1OTExMDg1OTQsInN1YiI6ImFueSBzdWJqZWN0Iiwic2NvcGUiOiJwcm90ZWN0ZWQ6cmVhZCBwcm90ZWN0ZWQ6d3JpdGUifQ.Cc0T3eJaYSjw_omMJ1WSbr8npSHYWS-Rb3JJCNz6pxTIbKv2vVvb6s5nQYsPRJ1i5n5KhEB8Hkpn3Q3oMMlJgtvJXR1AHX1Yt-JTm3qMMgI4_cNBwUQvs775GVirQ200rwbZj-IZ_b8ftegihz_PtSU0B5P4Zq1dz1-cplxX7dtHukvPCat0YQz2y0RgYSyOam3Q2yBzCqJ68aaR0vMgzLtCr2VBgMdvk1gzq2ZwDBJEfxuZkNXxgbpu9xkhOukzX0UlnT_mcYkK73cElFgcJSOYh68K70XR7-y3UTTChvxmz1QlzeVvqApYHzJnW6VVw5K6D61rIiQW91_8dFORGw"
    }
}