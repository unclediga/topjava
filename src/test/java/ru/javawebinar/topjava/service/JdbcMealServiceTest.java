package ru.javawebinar.topjava.service;

import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.Profiles;

@RunWith(SpringRunner.class)
@ActiveProfiles({Profiles.JDBC})
public class JdbcMealServiceTest extends AbstractMealServiceTest{
}
