package com.example.challenge_7.repo;

import com.example.challenge_7.entity.Receiver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ReceiverRepository extends JpaRepository<Receiver, String>, JpaSpecificationExecutor<Receiver> {
}
