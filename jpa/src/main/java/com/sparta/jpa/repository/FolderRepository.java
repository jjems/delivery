package com.sparta.jpa.repository;

import com.sparta.jpa.model.Folder;
import com.sparta.jpa.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FolderRepository extends JpaRepository<Folder, Long> {
    List<Folder> findAllByUser(User user);
}