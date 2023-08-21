# SpringBoot로 blog 만들기 - study01

## 체크

### 실수
1. sql의 구문 중 String 타입은 꼭 ''를 해주자.
2. sql 더미데이터를 만들 때, 쿼리 끝에 ;를 꼭 해주자.
3. select 쿼리 ORM 매핑 - DTO를 매핑하려면 qlrm해라
4. 늘 그렇지만, 오타 확인 필수!
5. 쿼리 실행 시, query.set 객체 잘 하기
6. list로 받은 객체들은 번지수 잘 체크하기
7. DTO를 request에 묶었으면, 꼭 static 했는 지 확인하기



### 오류
1. classpath:db/data.sql을 찾을 수 없다고 할 때 : 분명히 경로가 일치하고 존재하는데 계속 찾지 못한다고 할 때는 그냥 vscode를 껐다가 켜보고, 코드도 다시 지우고 붙여넣기 하자. vscode 자체의 문제이므로 이럴 때는 스트레스 덜 받자.
2. UserUpdate에서 null오류
```java
    // 회원수정 기능
    @PostMapping("/user/update")
    public String update(UserUpadateDTO userUpadateDTO, Integer id) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        User user = userRepository.findById(sessionUser.getId());

        userRepository.update(userUpadateDTO, sessionUser.getId());
        // id를 session에 등록된 id를 가지고 와야 된다.
        return "redirect:/";
    }
```
3. @GetMaping에서 select 쿼리 실행시, request에 꼭 데이터 넣기
 ```java
     // 글 수정 페이지
    @GetMapping("/board/{id}/updateForm")
    public String updateForm(@PathVariable Integer id, HttpServletRequest request){
        Board board = boardRepository.findById(id);
        request.setAttribute("board", board);
        // select한 결과를 mustache에게 보내주지 않으면, mustache는 데이터를 찾을 수 없다.
        return "board/updateForm";
    }
```