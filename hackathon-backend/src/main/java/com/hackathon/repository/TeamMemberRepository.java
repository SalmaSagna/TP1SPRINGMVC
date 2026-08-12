package com.hackathon.repository;

import com.hackathon.entities.Team;
import com.hackathon.entities.TeamMember;
import com.hackathon.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface TeamMemberRepository extends JpaRepository<TeamMember, Integer> {
    List<TeamMember> findByTeam(Team team);
    Optional<TeamMember> findByUser(User user);
    Optional<TeamMember> findByUserAndTeam(User user, Team team);
    boolean existsByUserAndTeam(User user, Team team);
}