package com.codeblock.creshbatch.jobs;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@RequiredArgsConstructor
@Configuration  //Spring에 설정 파일이라는 선언
public class TestJob {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job simpleJob() {
        return jobBuilderFactory.get("simpleJob")   //simpleJob 이름으로 batch job을 생성
                .start(simpleStep1())               //bean으로 등록된 Step을 실행
                .build();
    }

    @Bean
    public Step simpleStep1() {
        return stepBuilderFactory.get("simpleStep1")        //simpleStep1 batch step을 생성
                .tasklet((contribution, chunkContext) -> {  //step 안에서 수행될 기능들을 명시, tasklet은 step 안에서 단일로 수행될 커스텀한 기능을 선언할 때 사용
                    log.info(contribution.toString());
                    log.info(chunkContext.toString());
                    log.info(">>>>> This is Step1");
                    return RepeatStatus.FINISHED;           //batch가 성공적으로 수행되고 종료됨을 반환
                })
                .build();
    }
}
