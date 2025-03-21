package com.example.backend.config;

import com.example.backend.config.filter.LoginFilter;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.*;
import io.swagger.v3.oas.models.parameters.RequestBody;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Optional;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenApiCustomizer springSecurityLoginEndpointCustomizer(ApplicationContext applicationContext) {
        FilterChainProxy springSecurityFilterChain = applicationContext.getBean("springSecurityFilterChain", FilterChainProxy.class);

        return (openApi) -> {
            for (SecurityFilterChain filterChain : springSecurityFilterChain.getFilterChains()) {
                // 스프링 시큐리티의 특정 필터를 받아오는 부분
                Optional<LoginFilter> filter = filterChain.getFilters().stream()
                        .filter(LoginFilter.class::isInstance)
                        .map(LoginFilter.class::cast)
                        .findAny();
                if(filter.isPresent()) {
                    // 문서 설정 객체
                    Operation operation = new Operation();

                    // 문서에서 요청 설정
                    Schema<?> schema = new ObjectSchema()
                            .addProperty("email", new StringSchema().example("test@test.com"))  // email 예시 추가
                            .addProperty("password", new StringSchema().example("qwer1234"));    // password 예시 추가

                    RequestBody requestBody = new RequestBody().content(
                            new Content().addMediaType("application/json", new io.swagger.v3.oas.models.media.MediaType().schema(schema))
                    );

                    operation.setRequestBody(requestBody);


                    // 문서에서 응답 설정
                    ApiResponses responses = new ApiResponses();
                    responses.addApiResponse(
                            String.valueOf(HttpStatus.OK.value()),
                            new ApiResponse().description("로그인 성공")  // 200 OK 응답을 "로그인 성공"으로 설명
                    );
                    responses.addApiResponse(
                            String.valueOf(HttpStatus.BAD_REQUEST.value()),
                            new ApiResponse().description("로그인 실패")  // 400 Bad Request 응답을 "로그인 실패"로 설명
                    );
                    operation.setResponses(responses);



                    // 직접 만든 필터의 문서를 swagger에 등록
                    operation.addTagsItem("사용자 기능");
                    operation.summary("로그인");
                    operation.description("사용자가 로그인 하는 기능입니다.");

                    PathItem pathItem = new PathItem().post(operation);
                    openApi.getPaths().addPathItem("/login", pathItem);

                }

            }
        };
    }

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(apiInfo());
    }

    private Info apiInfo() {
        return new Info()
                .title("EduLink API")
                .description("Springdoc을 사용한 Swagger UI 테스트를 통해 EduLink 서비스의 백엔드 API를 테스트합니다.")
                .version("1.0.0");
    }
}