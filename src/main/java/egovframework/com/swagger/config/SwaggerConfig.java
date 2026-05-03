package egovframework.com.swagger.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/*
@Configuration
@EnableSwagger2
@EnableWebMvc
*/
public class SwaggerConfig {

	@Bean
	public Docket newsApiAll() {
		return new Docket(DocumentationType.SWAGGER_2).groupName("00.전체").apiInfo(apiInfo()).select()
				// .paths(PathSelectors.ant("/api/**"))
				.paths(PathSelectors.any()).build();
	}

	@Bean
	public Docket newsApiAccelerator1() {
		return new Docket(DocumentationType.SWAGGER_2).groupName("01.통합 사례 관리").apiInfo(apiInfo()).select()
				.paths(PathSelectors.ant("/itgcm/**")).build();
	}

	@Bean
	public Docket newsApiAccelerator2() {
		return new Docket(DocumentationType.SWAGGER_2).groupName("02.스마트 스크린 정보").apiInfo(apiInfo()).select()
				.paths(PathSelectors.ant("/smscm/**")).build();
	}
	
	@Bean
	public Docket newsApiAccelerator3() {
		return new Docket(DocumentationType.SWAGGER_2).groupName("03.자원관리").apiInfo(apiInfo()).select()
				.paths(PathSelectors.ant("/srccm/**")).build();
	}
	
	@Bean
	public Docket newsApiAccelerator4() {
		return new Docket(DocumentationType.SWAGGER_2).groupName("04.정보연계").apiInfo(apiInfo()).select()
				.paths(PathSelectors.ant("/infif/**")).build();
	}

	@Bean
	public Docket newsApiAccelerator5() {
		return new Docket(DocumentationType.SWAGGER_2).groupName("05.CYS-Net 종합정보망").apiInfo(apiInfo()).select()
				.paths(PathSelectors.ant("/cysns/**")).build();
	}
	
	@Bean
	public Docket newsApiAccelerator6() {
		return new Docket(DocumentationType.SWAGGER_2).groupName("06.꿈드림정보망").apiInfo(apiInfo()).select()
				.paths(PathSelectors.ant("/drmgs/**")).build();
	}
	
	@Bean
	public Docket newsApiAccelerator7() {
		return new Docket(DocumentationType.SWAGGER_2).groupName("07.행정지원시스템").apiInfo(apiInfo()).select()
				.paths(PathSelectors.ant("/pubms/**")).build();
	}
	
	@Bean
	public Docket newsApiAccelerator8() {
		return new Docket(DocumentationType.SWAGGER_2).groupName("08.개별(사례) 관리시스템").apiInfo(apiInfo()).select()
				.paths(PathSelectors.ant("/csems/**")).build();
	}
	
	@Bean
	public Docket newsApiAccelerator9() {
		return new Docket(DocumentationType.SWAGGER_2).groupName("09.위탁 관리시스템").apiInfo(apiInfo()).select()
				.paths(PathSelectors.ant("/subms/**")).build();
	}
	
	@Bean
	public Docket newsApiAccelerator10() {
		return new Docket(DocumentationType.SWAGGER_2).groupName("10.내일이룸학교").apiInfo(apiInfo()).select()
				.paths(PathSelectors.ant("/aimns/**")).build();
	}
	

	@Bean
	public Docket newsApiAccelerator11() {
		return new Docket(DocumentationType.SWAGGER_2).groupName("11.청소년사이버상담").apiInfo(apiInfo()).select()
				.paths(PathSelectors.ant("/couns/**")).build();
	}
	
	@Bean
	public Docket newsApiAccelerator12() {
		return new Docket(DocumentationType.SWAGGER_2).groupName("12.지자체안전망").apiInfo(apiInfo()).select()
				.paths(PathSelectors.ant("/regns/**")).build();
	}

	@Bean
	public Docket newsApiAccelerator13() {
		return new Docket(DocumentationType.SWAGGER_2).groupName("13.공통업무").apiInfo(apiInfo()).select()
				.paths(PathSelectors.ant("/comfd/**")).build();
	}
	
	@Bean
	public Docket newsApiAcceleratorSample() {
		return new Docket(DocumentationType.SWAGGER_2).groupName("99.Sample").apiInfo(apiInfo()).select()
				.paths(PathSelectors.ant("/sample/**")).build();
	}

	
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("위기 청소년 통합 지원 관리시스템 ").description("위기 청소년 통합 지원 시스템  입니다. ")
				.termsOfServiceUrl("https://추후 수정  해야함 URL.").license("Apache License Version 2.0")
				.licenseUrl("https://www.egovframe.go.kr").version("1.0").build();
	}
}