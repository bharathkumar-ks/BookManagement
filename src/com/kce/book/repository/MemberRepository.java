package com.kce.book.repository;
import java.util.ArrayList;
import com.kce.book.entity.Member;

public class MemberRepository {
    private ArrayList<Member> members = new ArrayList<>();
    public void addMember(Member member) {
        members.add(member);
    }

    public Member findMemberById(String memberId) {
        for (Member m : members) {
            if (m.getMemberId().equals(memberId)) {
                return m;
            }
        }
        return null;
    }

    public ArrayList<Member> getAllMembers() {
        return members;
    }
}