package com.project.MedicalRegister.Service;


import com.project.MedicalRegister.Model.Investigation;
import com.project.MedicalRegister.Repository.InvestigationRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.FactoryBasedNavigableListAssert.assertThat;


@IntegrationTest
@DataJpaTest
@Sql(statements = {
        "insert into medicalinvestigation (investigation_id, doctor_id, nume_investigatie, pret) values (1, 1, 'Blood Test', 50)",
        "insert into medicalinvestigation (investigation_id, doctor_id, nume_investigatie, pret) values (2, 1, 'Urine Test', 70)",
})
public class InvestigationRepoIntegrationTest {

    @Autowired
    private InvestigationRepo investigationRepository;

    @Test
    public void testFindAllInvestigations() {
        assert investigationRepository.findAll().size() == 2;

    }
}
