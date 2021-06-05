package ru.netology.patient.service.medical;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import ru.netology.patient.entity.BloodPressure;
import ru.netology.patient.entity.HealthInfo;
import ru.netology.patient.entity.PatientInfo;
import ru.netology.patient.repository.PatientInfoFileRepository;
import ru.netology.patient.repository.PatientInfoRepository;
import ru.netology.patient.service.alert.SendAlertService;
import ru.netology.patient.service.alert.SendAlertServiceImpl;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class MedicalServiceImplTest {
    BloodPressure currentPressure = new BloodPressure(60, 120);
    HealthInfo healthInfo = new HealthInfo(new BigDecimal("36.65"), new BloodPressure(120, 80));
    PatientInfo info= new PatientInfo("1","Иван", "Петров", LocalDate.of(1980, 11, 26),healthInfo);




    @Test
    void checkBloodPressure() {
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        SendAlertService sendAlertService = Mockito.mock(SendAlertServiceImpl.class);
        PatientInfoFileRepository repository = Mockito.mock(PatientInfoFileRepository.class);
        Mockito.when(repository.add(info)).thenReturn(info.getId());
        MedicalServiceImpl medicalService = new MedicalServiceImpl(repository,sendAlertService);
        medicalService.checkBloodPressure("1",currentPressure);
        Mockito.verify(sendAlertService).send(argumentCaptor.capture());
        Assertions.assertEquals("Warning, patient with id: 1, need help", argumentCaptor.getValue());
    }

    @Test
    void checkTemperature() {
    }
}