package com.lpdev.techbuddy.configuration;

import com.lpdev.techbuddy.model.entities.DevProfile;
import com.lpdev.techbuddy.model.entities.MentorProfile;
import com.lpdev.techbuddy.model.entities.User; // Importar a entidade User
import com.lpdev.techbuddy.model.enums.UserRole;
import com.lpdev.techbuddy.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Configuration
@Profile("test")
public class DataBasePopulation implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataBasePopulation(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {

        //boiler plate gerado por IA

        userRepository.deleteAll();

        List<User> usersToSave = new ArrayList<>();

        User userMentor1 = new User(null,"Ana Silva", "ana.silva@mentor.com", passwordEncoder.encode("senha123"), UserRole.MENTOR_BUDDY);
        MentorProfile mentor1 = new MentorProfile();
        mentor1.setProfileName("Ana Silva");
        mentor1.setHeadline("Engenheira de Software Sênior na TechCorp");
        mentor1.setProfileBio("Apaixonada por arquitetura de software e boas práticas de desenvolvimento. Mais de 10 anos de experiência com ecossistema Java e Cloud.");
        mentor1.setProfilePictureUrl("https://i.pravatar.cc/150?u=ana");
        mentor1.setProfileLocation("São Paulo, Brasil");
        mentor1.setProfileLinkedinUrl("https://linkedin.com/in/anasilva");
        mentor1.setProfileGithubUrl("https://github.com/anasilva");
        mentor1.setProfileStacks(new HashSet<>(Arrays.asList("Java", "Spring Boot", "AWS", "Docker", "Kubernetes")));
        mentor1.setExperienceYears(12);
        mentor1.setCompany("TechCorp");
        mentor1.setProfessionalTitle("Engenheira de Software Sênior");
        mentor1.setAvailableForMentoring(true);
        mentor1.setAverageRating(0.0);
        mentor1.setTotalMentorships(0L);
        // Associando as duas entidades
        userMentor1.setUserProfile(mentor1);
        mentor1.setUser(userMentor1);
        usersToSave.add(userMentor1);


        // Mentor 2: Bruno Costa
        User userMentor2 = new User(null, "Bruno Costa", "bruno.costa@mentor.com", passwordEncoder.encode("senha123"), UserRole.MENTOR_BUDDY);
        MentorProfile mentor2 = new MentorProfile();
        mentor2.setProfileName("Bruno Costa");
        mentor2.setHeadline("Desenvolvedor Frontend Especialista em React");
        mentor2.setProfileBio("Foco em criar interfaces reativas e performáticas. Ajudo desenvolvedores a dominar React, TypeScript e Next.js.");
        mentor2.setProfilePictureUrl("https://i.pravatar.cc/150?u=bruno");
        mentor2.setProfileLocation("Lisboa, Portugal");
        mentor2.setProfileLinkedinUrl("https://linkedin.com/in/brunocosta");
        mentor2.setProfileGithubUrl("https://github.com/brunocosta");
        mentor2.setProfileStacks(new HashSet<>(Arrays.asList("React", "TypeScript", "Next.js", "JavaScript", "CSS")));
        mentor2.setExperienceYears(8);
        mentor2.setCompany("Innovatech");
        mentor2.setProfessionalTitle("Frontend Specialist");
        mentor2.setAvailableForMentoring(true);
        mentor2.setAverageRating(0.0);
        mentor2.setTotalMentorships(0L);
        userMentor2.setUserProfile(mentor2);
        mentor2.setUser(userMentor2);
        usersToSave.add(userMentor2);


        // Mentor 3: Carla Dias (Indisponível)
        User userMentor3 = new User(null,"Carla Dias", "carla.dias@mentor.com", passwordEncoder.encode("senha123"), UserRole.MENTOR_BUDDY);
        MentorProfile mentor3 = new MentorProfile();
        mentor3.setProfileName("Carla Dias");
        mentor3.setHeadline("Data Scientist & Python Expert");
        mentor3.setProfileBio("Trabalho com Machine Learning e análise de dados. Posso ajudar com Python, Pandas, Scikit-learn e projetos de IA.");
        mentor3.setProfilePictureUrl("https://i.pravatar.cc/150?u=carla");
        mentor3.setProfileLocation("Rio de Janeiro, Brasil");
        mentor3.setProfileLinkedinUrl("https://linkedin.com/in/carladias");
        mentor3.setProfileGithubUrl("https://github.com/carladias");
        mentor3.setProfileStacks(new HashSet<>(Arrays.asList("Python", "Pandas", "Machine Learning", "SQL")));
        mentor3.setExperienceYears(6);
        mentor3.setCompany("Data Insights");
        mentor3.setProfessionalTitle("Data Scientist");
        mentor3.setAvailableForMentoring(false);
        mentor3.setAverageRating(0.0);
        mentor3.setTotalMentorships(0L);
        userMentor3.setUserProfile(mentor3);
        mentor3.setUser(userMentor3);
        usersToSave.add(userMentor3);


        // Mentor 4: Diego Fernandes
        User userMentor4 = new User(null, "Diego Fernandes", "diego.fernandes@mentor.com", passwordEncoder.encode("senha123"), UserRole.MENTOR_BUDDY);
        MentorProfile mentor4 = new MentorProfile();
        mentor4.setProfileName("Diego Fernandes");
        mentor4.setHeadline("DevOps e SRE | Google Cloud");
        mentor4.setProfileBio("Especialista em automação, CI/CD e infraestrutura como código. Vamos falar sobre Terraform, Jenkins e monitoramento?");
        mentor4.setProfilePictureUrl("https://i.pravatar.cc/150?u=diego");
        mentor4.setProfileLocation("Curitiba, Brasil");
        mentor4.setProfileLinkedinUrl("https://linkedin.com/in/diegofernandes");
        mentor4.setProfileGithubUrl("https://github.com/diegofernandes");
        mentor4.setProfileStacks(new HashSet<>(Arrays.asList("GCP", "Terraform", "CI/CD", "Docker", "Python")));
        mentor4.setExperienceYears(9);
        mentor4.setCompany("Cloud Masters");
        mentor4.setProfessionalTitle("SRE");
        mentor4.setAvailableForMentoring(true);
        mentor4.setAverageRating(0.0);
        mentor4.setTotalMentorships(0L);
        userMentor4.setUserProfile(mentor4);
        mentor4.setUser(userMentor4);
        usersToSave.add(userMentor4);


        // --- CRIAÇÃO DE DESENVOLVEDORES ---

        // Dev 1: Lucas Pereira
        User userDev1 = new User(null, "Lucas Pereira", "lucas.pereira@dev.com", passwordEncoder.encode("senha123"), UserRole.DEV_BUDDY);
        DevProfile dev1 = new DevProfile();
        dev1.setProfileName("Lucas Pereira");
        dev1.setHeadline("Estudante de Análise e Desenvolvimento de Sistemas");
        dev1.setProfileBio("Iniciando minha jornada no backend com Java e Spring. Buscando minha primeira oportunidade no mercado.");
        dev1.setProfilePictureUrl("https://i.pravatar.cc/150?u=lucas");
        dev1.setProfileLocation("Belo Horizonte, Brasil");
        dev1.setProfileLinkedinUrl("https://linkedin.com/in/lucaspereira");
        dev1.setProfileGithubUrl("https://github.com/lucaspereira");
        dev1.setProfileStacks(new HashSet<>(Arrays.asList("Java", "Spring Boot", "SQL")));
        dev1.setLearningGoals("Conseguir meu primeiro emprego como dev Java Jr. e aprender sobre cloud.");
        userDev1.setUserProfile(dev1);
        dev1.setUser(userDev1);
        usersToSave.add(userDev1);


        // Dev 2: Fernanda Lima
        User userDev2 = new User(null,  "Fernanda Lima",  "fernanda.lima@dev.com", passwordEncoder.encode("senha123"), UserRole.DEV_BUDDY);
        DevProfile dev2 = new DevProfile();
        dev2.setProfileName("Fernanda Lima");
        dev2.setHeadline("Migrando de carreira para Frontend");
        dev2.setProfileBio("Vindo da área de design, estou aprendendo a programar para criar interfaces incríveis. Focada em React e JavaScript.");
        dev2.setProfilePictureUrl("https://i.pravatar.cc/150?u=fernanda");
        dev2.setProfileLocation("Porto Alegre, Brasil");
        dev2.setProfileLinkedinUrl("https://linkedin.com/in/fernandalima");
        dev2.setProfileGithubUrl("https://github.com/fernandalima");
        dev2.setProfileStacks(new HashSet<>(Arrays.asList("HTML", "CSS", "JavaScript", "React")));
        dev2.setLearningGoals("Construir um portfólio sólido e entender como conectar o front com APIs REST.");
        userDev2.setUserProfile(dev2);
        dev2.setUser(userDev2);
        usersToSave.add(userDev2);

        // --- SALVANDO TUDO DE UMA VEZ ---
        userRepository.saveAll(usersToSave);

        System.out.println("✅ Base de dados de teste populada CORRETAMENTE! Users e Profiles associados.");
    }
}