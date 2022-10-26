//package ar.edu.itba.getaway.models;
//
//import javax.persistence.*;
//import java.util.List;
//
//@Entity
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
//public class UserModelWithReviews extends UserModel{
//    @OneToMany(fetch = FetchType.LAZY)
//    @JoinColumn(name = "userId")
//    private List<ReviewModel> userReviews;
//
//    public List<ReviewModel> getUserReviews(Integer page, Integer page_size) {
//        Integer fromIndex = (page - 1) * page_size;
//        Integer toIndex = Math.min((fromIndex + page_size), userReviews.size());
//        return userReviews.subList(fromIndex, toIndex);
//    }
//
//    public Integer getReviewCount(){
//        return userReviews.size();
//    }
//}
