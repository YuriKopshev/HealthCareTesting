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

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class MedicalServiceImplTest {
    BloodPressure currentPressure = new BloodPressure(60, 120);
    HealthInfo healthInfo = new HealthInfo(new BigDecimal("36.65"), new BloodPressure(120, 80));
    PatientInfo info= new PatientInfo("1","Иван", "Петров", LocalDate.of(1980, 11, 26),healthInfo);
    BigDecimal currentTemperature = new BigDecimal("34.9");
    BigDecimal normalTemperature = new BigDecimal("36.6");



    @Test
    void checkBloodPressure() {
        String id1 = "1";
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        PatientInfoFileRepository repository = Mockito.mock(PatientInfoFileRepository.class);
        Mockito.when(repository.getById(id1)).thenReturn(info);
        SendAlertService sendAlertService = Mockito.mock(SendAlertServiceImpl.class);
        MedicalServiceImpl medicalService = new MedicalServiceImpl(repository,sendAlertService);
        medicalService.checkBloodPressure("1",currentPressure);
        Mockito.verify(sendAlertService).send(argumentCaptor.capture());
        assertEquals("Warning, patient with id: 1, need help",argumentCaptor.getValue());
    }

    @Test
    void checkTemperature() {
        String id1 = "1";
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        PatientInfoFileRepository repository = Mockito.mock(PatientInfoFileRepository.class);
        Mockito.when(repository.getById(id1)).thenReturn(info);
        SendAlertService sendAlertService = Mockito.mock(SendAlertServiceImpl.class);
        MedicalServiceImpl medicalService = new MedicalServiceImpl(repository,sendAlertService);
        medicalService.checkTemperature("1",currentTemperature);
        Mockito.verify(sendAlertService).send(argumentCaptor.capture());
        assertEquals("Warning, patient with id: 1, need help",argumentCaptor.getValue());
    }

    @Test
    void checkNormalTemperature() {
        String id1 = "1";
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        PatientInfoFileRepository repository = Mockito.mock(PatientInfoFileRepository.class);
        Mockito.when(repository.getById(id1)).thenReturn(info);
        SendAlertService sendAlertService = Mockito.mock(SendAlertServiceImpl.class);
        MedicalServiceImpl medicalService = new MedicalServiceImpl(repository,sendAlertService);
        medicalService.checkTemperature("1",normalTemperature);
        Mockito.verify(sendAlertService).send(argumentCaptor.capture());
        assertNull(argumentCaptor.getValue());

    }
}
