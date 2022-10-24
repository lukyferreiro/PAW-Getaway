//package ar.edu.itba.getaway.models;
//
//import org.hibernate.annotations.Formula;
//
//import javax.persistence.*;
//import java.util.List;
//import java.util.Objects;
//
//@Entity
//@Table(name = "experiences")
//public class ExperienceModelWithReviews extends ExperienceModel{
//    @OneToMany(fetch = FetchType.LAZY)
//    @JoinColumn(name = "experienceId")
//    private List<ReviewModel> experienceReviews;
//
//    public List<ReviewModel> getExperienceReviews(Integer page, Integer page_size) {
//        Integer fromIndex = (page - 1) * page_size;
//        Integer toIndex = Math.min((fromIndex + page_size), experienceReviews.size());
//        return experienceReviews.subList(fromIndex, toIndex);
//    }
//}
