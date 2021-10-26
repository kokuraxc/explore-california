package com.zhengjin.guo.explorecalifornia.domain;

import java.io.IOException;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import com.fasterxml.jackson.databind.ObjectMapper;

@Entity
public class TourRating {
    @EmbeddedId
    private TourRatingPk pk;

    @Column(nullable = false)
    private Integer score;

    @Column
    private String comment;

    public TourRating() {
    }

    public TourRating(TourRatingPk pk, Integer score, String comment) {
        this.pk = pk;
        this.score = score;
        this.comment = comment;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || obj.getClass() != this.getClass())
            return false;
        TourRating that = (TourRating) obj;
        return Objects.equals(pk, that.pk) && Objects.equals(score, that.score)
                && Objects.equals(comment, that.comment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pk, score, comment);
    }

    @Override
    public String toString() {
        ObjectMapper om = new ObjectMapper();
        try {
            return om.writeValueAsString(this);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public TourRatingPk getPk() {
        return pk;
    }

    public void setPk(TourRatingPk pk) {
        this.pk = pk;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
