<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="board.model.vo.*" %>
<%
	Board b = (Board)request.getAttribute("board");
%>
<%@ include file="/WEB-INF/views/common/header.jsp" %>    
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/board.css" />

<script>
$(function(){
	$("[name=boardUpdateFrm]").submit(boardValidate);
	
	/**
	* 첨부파일 새로 추가시 기존파일 삭제 체크여부 결정하기
	*/
	$("[name=upFile]").change(function(){
		var $upFile = $(this);
		console.log($upFile.val());
		if($upFile.val()){
			//파일을 선택한 경우 #delFile체크
			$("#delFile").prop("checked", true);
		}
		else {
			//파일선택을 취소한 경우 #delFile체크해제
			$("#delFile").prop("checked", false);
		}
	});
	
});
function boardValidate(){
	var $content = $("[name=boardContent]");
	if(/^(.|\n)+$/.test($content.val()) == false){
		alert("내용을 입력하세요");
		return false;
	}
	return true;
}
function boardView(){
	history.go(-1);
}
</script>
<section id="board-container">
<h2>게시판 수정</h2>
<form 
	action="<%=request.getContextPath() %>/board/boardUpdate" 
	method="post" 
	enctype="multipart/form-data"
	name="boardUpdateFrm">
	<input type="hidden" name="boardNo" value="<%=b.getBoardNo() %>" />
	<table id="tbl-board-view">
	<tr>
		<th>제 목</th>
		<td><input type="text" name="boardTitle" value="<%=b.getBoardTitle()%>" required></td>
	</tr>
	<tr>
		<th>작성자</th>
		<td>
			<%=memberLoggedIn.getMemberName()%>
			<input type="hidden" name="boardWriter" value="<%=memberLoggedIn.getMemberId()%>"/>
		</td>
	</tr>
	<tr>
		<th>첨부파일</th>
		<td>
			<!-- input:file은 보안상의 이유로 value속성을 임의조작할 수 없다. -->
			<input type="file" name="upFile" value="">
			<%-- 기존 첨부파일이 있는 경우만 노출 --%>
			<% if(b.getBoardOriginalFileName() != null){ %>
			<input type="hidden" name="oldBoardOriginalFileName" value="<%= b.getBoardOriginalFileName() %>" />
			<input type="hidden" name="oldBoardRenamedFileName" value="<%= b.getBoardRenamedFileName() %>" />
			<p>
				<%= b.getBoardOriginalFileName() %>&nbsp;&nbsp;
				<label for="delFile">삭제</label>
				<input type="checkbox" name="delFile" id="delFile" value="<%= b.getBoardRenamedFileName() %>"/>
			</p>
			<% } %>
		</td>
	</tr>
	<tr>
		<th>내 용</th>
		<td><textarea rows="5" cols="50" name="boardContent"><%=b.getBoardContent()%></textarea></td>
	</tr>
	<tr>
		<th colspan="2">
			<input type="submit" value="수정하기">
			<input type="button" value="취소" onclick="boardView();">
		</th>
	</tr>
</table>
</form>
</section>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>
