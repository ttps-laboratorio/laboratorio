package com.ttps.laboratorio.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ttps.laboratorio.entity.HealthInsurance;
import com.ttps.laboratorio.repository.IHealthInsuranceRepository;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class HealthInsuranceServiceTest {

  IHealthInsuranceRepository healthInsuranceRepository = Mockito.mock(IHealthInsuranceRepository.class);

  HealthInsuranceService service;

  @BeforeEach
  void setUp(){
    this.service = new HealthInsuranceService(healthInsuranceRepository);
  }

  @Test
  public void shouldReturnListOfHealthInsurances() {
    List<HealthInsurance> healthInsurances = createListOfHealthInsurances();

    when (healthInsuranceRepository.findAll()).thenReturn(healthInsurances);

    assertEquals(healthInsurances,service.getAllHealthInsurances());
    verify(healthInsuranceRepository,atLeast(1)).findAll();
  }

  private List<HealthInsurance> createListOfHealthInsurances() {
    List<HealthInsurance> healthInsurances = new ArrayList<>();
    healthInsurances.add(new HealthInsurance() {
      {
        setId(1L);
        setName("one");
        setEmail("one@email.com");
        setPhoneNumber(123);
      }
    });
    healthInsurances.add(new HealthInsurance() {
      {
        setId(2L);
        setName("two");
        setEmail("two@email.com");
        setPhoneNumber(123);
      }
    });
    healthInsurances.add(new HealthInsurance() {
      {
        setId(3L);
        setName("three");
        setEmail("three@email.com");
        setPhoneNumber(123);
      }
    });
    return healthInsurances;
  }


}
