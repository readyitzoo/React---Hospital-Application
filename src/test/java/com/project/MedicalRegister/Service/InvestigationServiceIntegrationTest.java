package com.project.MedicalRegister.Service;

import com.project.MedicalRegister.Model.Investigation;
import com.project.MedicalRegister.DTO.InvestigationDto;
import com.project.MedicalRegister.Repository.InvestigationRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class InvestigationServiceIntegrationTest {

    @Mock
    private InvestigationRepo investigationRepository;
    @InjectMocks
    private InvestigationService investigationService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @Transactional
    public void testAddInvestigationToDoctor() {

        InvestigationDto investigationDto = new InvestigationDto();
        investigationDto.setDoctorId(1L);
        investigationDto.setInvestigationName("Blood Test");
        investigationDto.setPrice(50L);

        when(investigationRepository.save((Investigation) any())).thenAnswer(invocation -> {
            Investigation argument = invocation.getArgument(0);
            argument.setInvestigationId(1L);
            argument.setDoctorId(1L);
            argument.setInvestigationName("Blood Test");
            argument.setPrice(50L);

            return argument;
        });

        Investigation savedInvestigation = investigationService.addInvestigationToDoctor(investigationDto);

        verify(investigationRepository, times(1)).save(any(Investigation.class));

        assert savedInvestigation.getInvestigationId() != null;
        assert savedInvestigation.getInvestigationName().equals("Blood Test");
        assert savedInvestigation.getPrice().equals(50L);
    }
}
