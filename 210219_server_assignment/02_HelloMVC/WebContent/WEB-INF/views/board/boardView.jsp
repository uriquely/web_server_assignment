<%@page import="board.model.vo.BoardComment"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="board.model.vo.Board"%>
<% 
	Board board = (Board)request.getAttribute("board");
	List<BoardComment> commentList = (List<BoardComment>)request.getAttribute("commentList");
%>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<script>
$(function(){
	/**
	* 로그인 하지 않고 댓글을 작성할 수 없다.
	*/
	$("[name=boardCommentContent]").focus(function(){
		<%-- if(<%= memberLoggedIn == null %>)
			loginAlert(); --%>
		
		<% if(memberLoggedIn == null){%>
			loginAlert();
		<% } %>
	});
	
	/**
	* 폼 제출 유효성 검사
	* 1. return false;
	* 2. event.preventDefault();
	*/
	$("[name=boardCommentFrm]").submit(function(e){
		var $boardCommentContent = $("[name=boardCommentContent]");
		if(/^(.|\n){1,}$/.test($boardCommentContent.val()) == false){
			alert("댓글 내용을 작성해주세요.");
			$boardCommentContent.focus();
			e.preventDefault();
		}
	});
	
	
});

function loginAlert(){
	alert("로그인후 댓글을 작성할 수 있습니다.");
	$("#memberId").focus();
}

function fileDownload(oName, rName){
	console.log(oName, rName);
	location.href = "<%= request.getContextPath() %>/board/fileDownload?oName=" + oName + "&rName=" + rName; 
}
</script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/board.css" />
<section id="board-container">
	<h2>게시판</h2>
	<table id="tbl-board-view">
		<tr>
			<th>글번호</th>
			<td><%= board.getBoardNo() %></td>
		</tr>
		<tr>
			<th>제 목</th>
			<td><%= board.getBoardTitle() %></td>
		</tr>
		<tr>
			<th>작성자</th>
			<td><%= board.getBoardWriter() %></td>
		</tr>
		<tr>
			<th>조회수</th>
			<td><%= board.getBoardReadCount() %></td>
		</tr>
		<tr>
			<th>첨부파일</th>
			<td>
			<%-- 첨부파일이 있을경우만, 이미지와 함께 original파일명 표시 --%>
			<% if(board.getBoardOriginalFileName() != null){ %>
				<a href="javascript:fileDownload('<%=board.getBoardOriginalFileName() %>', '<%= board.getBoardRenamedFileName() %>')">
					<img alt="첨부파일" src="<%=request.getContextPath() %>/images/file.png" width=16px>
					<%= board.getBoardOriginalFileName() %>
				</a>
			<% } %>
			</td>
		</tr>
		<tr>
			<th>내 용</th>
			<td><%= board.getBoardContent() %></td>
		</tr>
		<%-- 작성자와 관리자만 마지막행 수정/삭제버튼이 보일수 있게 할 것 --%>
		<% if(
			memberLoggedIn != null
		 && (memberLoggedIn.getMemberId().equals(board.getBoardWriter())
		 	|| MemberService.ADMIN_MEMBER_ROLE.equals(memberLoggedIn.getMemberRole()))
		){ %>
		<tr>
			<th colspan="2">
				<input type="button" value="수정하기" onclick="updateBoard();"/> 
				<input type="button" value="삭제하기" onclick="deleteBoard();" />
			</th>
		</tr>
		<script>
		function updateBoard(){
	        location.href="<%=request.getContextPath()%>/board/boardUpdate?boardNo=<%=board.getBoardNo() %>";
		}
		
		function deleteBoard(){
			if(confirm("이 게시물을 삭제하시겠습니까?")){
				$("[name=boardDelFrm]").submit();
			}
		}
		</script>
		<form 
			action="<%= request.getContextPath() %>/board/boardDelete"
			method="POST"
			name="boardDelFrm">
			<input type="hidden" name="boardNo" value="<%= board.getBoardNo() %>"/>
			<input type="hidden" name="rName" value="<%= board.getBoardRenamedFileName() != null ? board.getBoardRenamedFileName() : "" %>" />
		</form>
		<% } %>
	</table>
	
	
	<hr style="margin-top:30px;" />	
    
	<div class="comment-container">
        <div class="comment-editor">
            <form action="<%=request.getContextPath()%>/board/boardCommentInsert" method="post" name="boardCommentFrm">
                <input type="hidden" name="boardRef" value="<%= board.getBoardNo() %>" />
                <input type="hidden" name="boardCommentWriter" value="<%= memberLoggedIn != null ? memberLoggedIn.getMemberId() : "" %>" />
                <input type="hidden" name="boardCommentLevel" value="1" />
                <input type="hidden" name="boardCommentRef" value="0" />    
				<textarea name="boardCommentContent" cols="60" rows="3"></textarea>
                <button type="submit" id="btn-insert">등록</button>
            </form>
        </div>
        
		<!--table#tbl-comment-->
		
		<table id="tbl-comment">
		<% if(commentList == null) { %>
			<%--조회된 결과가 없는 경우 --%>
			<tr>
				<td colspan="10" style="text-align:center;">
					조회된 결과가 없습니다.
				</td>
			</tr>
		<% } else { %>
			<% for(BoardComment bcm : commentList) { %>
				<% if(bcm.getBoardCommentRef() == 0) {%>
				
			<%--조회된 결과가 있는 경우 --%>	
			<%-- 댓글인 경우 tr.level1 --%>
		  	<tr class=level1>
				<td>
					<sub class=comment-writer><%=bcm.getBoardCommentWriter() %></sub>
					<sub class=comment-date><%=bcm.getBoardCommentDate() %></sub>
				<br />
					<%-- 댓글내용 --%>
					<%=bcm.getBoardCommentContent() %>
				</td>
				<td>
					<button class="btn-reply" value="댓글번호">답글</button>
					<button class="btn-delete" value="댓글번호">삭제</button>
				</td>
			</tr>
			<% } else { %>
			<%-- 대댓글인 경우 tr.level2 --%>
			<tr class=level2>
				<td>
					<sub class=comment-writer><%=bcm.getBoardCommentWriter() %></sub>
					<sub class=comment-date><%=bcm.getBoardCommentDate() %></sub>
				<br />
					<%-- 대댓글 내용 --%>
					<%=bcm.getBoardCommentContent() %>
				</td>
				<td></td>
			</tr>
	<% 		} 
		}
	  } %>
		</table>
	</div>
	
	
	
	
</section>


<%@ include file="/WEB-INF/views/common/footer.jsp" %>
