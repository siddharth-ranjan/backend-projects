package com.hackathon.markdownnotetakingapp.repository;

import com.hackathon.markdownnotetakingapp.model.NoteModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<NoteModel, Long> {

}