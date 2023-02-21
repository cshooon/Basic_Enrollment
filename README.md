# Basic_Enrollment
회원가입과 수강신청이 가능한 웹페이지입니다.

## Contents
1. [Abstract and Introduction](#Abstract-and-Introduction)
2. [Background and Motivation](#Background-and-Motivation)
3. [Design and Implementation](#Design-and-Implementation)
4. [Evaluation](#Evaluation)
5. [Conclusion](#Conclusion)

## Abstract and Introduction
### Abstract
회원가입이 가능한 가상의 수강신청 사이트를 만들었다. 수강신청을 하고자 하는 과목을 찾을 때학점이나 강의평과 같은 강의 정보로는 검색이 불가능하다. 특정한 조건으로 검색할 수 있고 다양한 조건으로 검색할 수 있는 사이트를 만들고자 했다. 회원가입부분에서는 회원 가입, 정보 수정, 회원 탈퇴를 구현했다. 기존 수강신청 기능에서 검색 기능을 추가해서 구현하려고 했다. 수강신청 기능이 구현에서 오류가 생겨 검색 기능까지는 실행하지 못했다. 
### Introduction
수강신청에 성공하면 원하는 시간에 공부를 하면서 나머지 시간을 효율적을 쓸 수 있다. 배우고자 하는 과목을 공부하면서 만족감과 편안함을 느낄 수 있다. 서울과학기술대학교 수강신청 사이트에서는 학점순(3학점인지 2학점인지), 강의평순으로 검색하는 기능이 없다. 다음은 선행연구의 집합체인 서울과학기술대학교 수강신청 홈페이지이다.
![Enrollment](/images/수강신청.png)
연도, 학기, 학과, 요일, 과목명으로 검색을 할 수 있다. 주야는 주간과 야간이고 구분은 cyber인지 교직인지 영어전용인지 구분해준다. 수강신청을 하고 검색결과가 나왔을 때 강의계획서 조회수, 학점순, 강의평가순으로 정렬해주는 사이트를 만들고자 한다. 수강신청 사이트에 맞게 ER-model을 만들었고 언어는 자바를 사용해서 만들었다.
Section 2에서는 Background와 Motivation에 대해 설명하고 Section 3에서는 수강신청 사이트의 Design과 Implementation에 대해 설명한다. Section 4에서는 Evaluation, Section 5에서는 결론을 내리며 마무리한다.

## Background and Motivation
수강신청은 대학에서 매 학기가 시작하기 전에 자신이 들을 과목을 정하여 신청하는 것을 말한다. 수강신청 방식은 학교마다 다르지만 우리 학교의 경우는 전 학년이 같은 날 같은 시간에 한 번에 신청한다. 수강신청을 하기 위해 전공필수 과목을 찾던 중 불편함을 느꼈다. 2학점이 부족해서 2학점 과목만 찾으려고 하는데 3학점 과목까지 섞어 나와 불편했다. 그리고 강의평이 좋은 교수님 과목을 신청하려고 하는데 강의평을 보려면 강의계획서를 하나하나 찾는데 시간이 걸려서 불편했다.

## Design and Implementation
### Design
![ER](/images/ER.png)
위 그림은 수강신청 사이트를 만들기 위한 ER-model이다. Subject는 과목이고 Course는 강좌이다. 한 과목이 여러 개 개설될 수 있지만 한 강좌에서 두 과목을 다룰 수는 없기 때문에 Subject 테이블과 Course 테이블은 일대다 관계이다. Subject는 Subject_ID를 Course는 Course_ID를 PK로 가진다. 한 학생이 여러 과목을 수강할 수 있고 한 강좌를 여러 학생이 들을 수 있으므로 Student와 Course는 다대다 관계이다. 
```java
public int insertMember(MembersDTO dto) {
		int result = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		String query = "insert into Student values(?, ?, ?, ?)";
		try {
			// db parameters
			Class.forName("org.sqlite.JDBC");
			String url = "jdbc:sqlite:C:/sqlite/DB/Enrollment.db";
			// create a connection to the database
			conn = DriverManager.getConnection(url);
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, dto.getId());
			pstmt.setString(2, dto.getPw());
			pstmt.setString(3, dto.getName());
			pstmt.setInt(4, dto.getCredit());
			result = pstmt.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			close(pstmt);
			close(conn);
		}
		return result;
	}
```
구현을 위해 java랑 유사한 jsp를 활용하였다. DTO와 DAO 구조를 활용해서 DTO에는 데이터 베이스에 있는 정보를 저장하기 위한 getter와 setter, 생성자를 만들었다. DAO에는 데이터베이스에 접근하기 위한 connection과 query문을 만들었다. 
우선 회원가입 부분이다. Connection을 받는 부분은 sqlite3에 맞게 강의 슬라이드 내용을 바꾸었다. 회원가입을 하는 메소드이다. Student table에 query문을 날려 ID, PW, 이름, 그리고 총 학점을 받아서 넣어준다. 
```java
String query = "select Student_ID from Student where Student_ID = ?";
```
회원가입을 할 때 같은 아이디가 없도록 하기 위해서 Student 테이블에 같은 아이디가 있는지 확인하는 메소드를 만들었다.
```java
String query="select Student_PW from Student where Student_ID=?";
```
Id와 pw를 받아서 DB에 연결 후 id로 pw 데이터를 얻어와 pw가 일치하는지 확인하는 메소드이다. 아이디가 존재하지 않는다면 -1, 존재하지만 비밀번호가 틀린 경우 0, 비밀 번호가 맞는 경우 1을 반환한다.
```java
String query = "select * from Student where Student_ID=?";
```
Student table의 비밀번호를 수정해주는 메소드이다. 이름은 수정할 수 없다.
```java
String query = "update Student set Student_PW = ? where Student_ID = ?";
```
Id로 레코드를 찾아서 삭제해준다.
```java
String query = "delete from Student where Student_ID=?";
```
다음은 수강신청 메소드이다. Subject는 Course의 Course_ID를 FK로 가지므로 natural join을 해주면 겹치는 column 없이 출력된다. 다음은 수강신청을 하는 메소드이다. Enrollment table에 insert해준다.
```java
String query = "select * from Course natural join Subject";
String query = "insert into Enrollment values(?, ?, ?)";
```
수강신청 취소하는 메소드이다. Enrollment table에서 delete해준다. PK인 Student_ID와 Course_ID로 레코드를 찾는다.
```java
String query = "delete from Enrollment where Student_ID=? and Course_ID=?";
```
수강신청한 목록 보여주는 메소드이다. Enrollment의 Student_ID, Course_ID, Subject_ID는 모두 FK이다. 겹치는 column 없이 출력하기 위해 natural join을 사용해준다.
```java
String query = "select * from Enrollment natural join Course natural join Subject where Student_ID=?";
```
### Implementation
![first](/images/first.png)
![second](/images/second.png)
![third](/images/third.png)


왼쪽 그림은 tomcat을 실행했을 때 첫 화면이다. 오른쪽 화면처럼 회원가입을 한 뒤 로그인을 할 수 있다.
![fourth](/images/fourth.png)


로그아웃을 하면 다시 로그인페이지로 돌아가고 session.invalidate을 통해 로그아웃 해준다. 우선수강신청하기를 누른다.
![fifth](/images/fifth.png)


수강신청 가능한 과목은 DB Browser을 통해 넣었다. Checkbox로 중복 체크이 가능하도록 만들었다. 체크한 뒤에 상단에 있는 enroll을 누르면 수강신청이 된다.
![sixth](/images/sixth.png)


수강신청 현황을 보여주는 화면으로 넘어간다. 왼쪽 위 delete을 통해 삭제할 수 있다.
![seventh](/images/seventh.png)
![eleventh](/images/eleventh.png)

Delete을 누르면 삭제 성공!!이라는 메시지가 뜨고 삭제가 된다.


main에서 정보 수정에 들어가면 비밀번호를 바꿀 수 있다.


![eighth](/images/eighth.png)


Main의 오른쪽에 있는 회원 탈퇴 버튼을 누르면 회원 탈퇴가 되고 아래 DB browser에서 확인하면 아이디가 20101301인 회원이 사라졌음을 알 수 있다.


![nineth](/images/nineth.png)
## Evaluation
회원가입과 로그인, 그리고 회원 정보 수정과 탈퇴를 구현했다. 로그인의 경우 오류 없이 잘 작동한다. 수강신청 삭제에서 form 태그를 통해 번호를 받아서 해당하는 번호를 삭제하도록 만들었다. 삭제가 진행되면서 번호가 바뀌었는데 이 점이 적용되지 않고 번호가 그대로 넘어가서 삭제가 잘 되지 않았다. 이 지점에서 막혀 검색 기능까지는 구현하지 못하였다. 
## Conclusion
학점 별로, 강의 평 좋은 순서대로 검색할 수 있는 기능은 구현하지 못했다. 하지만 로그인 구현이나 수강신청 ER model이 초심자에게 도움이 되기 바란다. 수강신청 사이트가 간단해 보이지만 만들기 어렵고 고려해야 하는 점(시간, 학점)이 매우 많다는 점을 알게 되었다. 최대한 간단하게 만든 웹페이지인데도 404, 505 등 수많은 에러들을 만났다. 공부를 더 한 뒤 이 코드를 발전시켜 검색 기능까지 구현해보고 싶다. 

