package org.annill.linguabot.repository;

import org.annill.linguabot.model.entity.Folder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FolderRepository extends JpaRepository<Folder, Long> {

    @Query("SELECT f FROM Folder f WHERE f.name = :name")
    Folder getFolderByName(String name);
}
