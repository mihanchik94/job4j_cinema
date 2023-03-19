package ru.job4j.cinema.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.sql2o.Sql2o;
import ru.job4j.cinema.configuration.DataSourceConfiguration;
import ru.job4j.cinema.model.File;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import static org.assertj.core.api.Assertions.*;

class Sql2oFileRepositoryTest {
    static Sql2oFileRepository sql2oFileRepository;
    static Collection<File> expectedFiles;


    @BeforeAll
    public static void initRepository() throws IOException {
        Properties properties = new Properties();
        try (InputStream inputStream = Sql2oFileRepository.class.getClassLoader().getResourceAsStream("connection.properties")) {
            properties.load(inputStream);
        }
        String url = properties.getProperty("database.url");
        String username = properties.getProperty("database.username");
        String password = properties.getProperty("database.password");

        DataSourceConfiguration configuration = new DataSourceConfiguration();
        DataSource dataSource = configuration.connectionPool(url, username, password);
        Sql2o sql2o = configuration.dataBaseClient(dataSource);

        sql2oFileRepository = new Sql2oFileRepository(sql2o);
    }

    @BeforeAll
    public static void fillExpectedFiles() {
        File file1 = new File(1, "1+1", "db/files/1+1.jpg");
        File file2 = new File(2, "Двадцать одно", "db/files/21.jpg");
        File file3 = new File(3, "Клик с пультом по жизни", "db/files/click.jpg");
        File file4 = new File(4, "Джентельмены удачи", "db/files/djentelmeny_udachi.jpg");
        File file5 = new File(5, "Дом который построил Джек", "db/files/dom_kotory_postroil_jack.jpg");
        File file6 = new File(6, "Еще по одной", "db/files/esche_po_odnoy.jpg");
        File file7 = new File(7, "Евротур", "db/files/eurotour.jpg");
        File file8 = new File(8, "Форест Гамп", "db/files/forest_gamp.jpg");
        File file9 = new File(9, "Кингсман", "db/files/kingsman.jpg");
        File file10 = new File(10, "Леон", "db/files/leon.jpg");
        File file11 = new File(11, "Мальчишник в Вегасе", "db/files/malchishnik_v_veg.jpg");
        File file12 = new File(12, "Маска", "db/files/maska.jpg");
        File file13 = new File(13, "Зеркала", "db/files/mirrors.jpg");
        File file14 = new File(14, "Мы Миллеры", "db/files/my_millery.jpg");
        File file15 = new File(15, "Кошмар на улице Вязов", "db/files/nightmare_on_elm_street.jpg");
        File file16 = new File(16, "Сайлент Хилл", "db/files/silent_hill.jpg");
        File file17 = new File(17, "Стрингер", "db/files/stringer.jpg");
        File file18 = new File(18, "Роковое число 23", "db/files/the_number_23.jpg");
        File file19 = new File(19, "Троя", "db/files/troy.jpg");
        File file20 = new File(20, "В погоне за счастьем", "db/files/v_pogone_za_sch.jpg");
        File file21 = new File(21, "Васаби", "db/files/wasabi.jpg");
        expectedFiles = List.of(file1, file2, file3, file4, file5, file6, file7, file8, file9, file10,
                file11, file12, file13, file14, file15, file16, file17, file18, file19, file20, file21);
    }


    @Test
    public void whenFindByIdThenGetFile() {
        int expectedFileId = 15;
        File expectedFile = new File(15, "Кошмар на улице Вязов", "db/files/nightmare_on_elm_street.jpg");
        File actualFile = sql2oFileRepository.findById(expectedFileId).get();
        Collection<File> actualFiles = sql2oFileRepository.findAll();
        assertThat(actualFile).usingRecursiveComparison().isEqualTo(expectedFile);
        assertThat(actualFiles).isEqualTo(expectedFiles);
    }

    @Test
    public void whenFindNonExistedFileThenEmptyOptional() {
        assertThat(sql2oFileRepository.findById(28)).isEqualTo(Optional.empty());
    }
}