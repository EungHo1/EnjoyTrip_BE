<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ko" data-theme="light"> <%-- Pico.css 다크/라이트 모드 설정 --%>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>EnjoyTrip</title>

    <%-- 1. Pico.css CDN 링크 --%>
    <link
            rel="stylesheet"
            href="https://cdn.jsdelivr.net/npm/@picocss/pico@2/css/pico.min.css"
    >
    <%-- 2. 우리만의 커스텀 CSS (Pico를 덮어쓰거나 추가할 스타일) --%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/main.css">
</head>
<body>
<%-- Pico.css의 기본 레이아웃을 위한 container 클래스 --%>
<main class="container">