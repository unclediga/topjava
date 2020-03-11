package ru.javawebinar.topjava.service;

import org.junit.runner.RunWith;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.ActiveDbProfileResolver;
import ru.javawebinar.topjava.Profiles;

@RunWith(SpringRunner.class)
@ActiveProfiles({Profiles.JDBC})
public class JdbcUserServiceTest extends AbstractUserServiceTest{
}
