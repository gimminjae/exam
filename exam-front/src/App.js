import React from 'react'
import './App.css';
import Router from "./router";
import {BrowserRouter, Link} from "react-router-dom";

function App() {
  return (
    <BrowserRouter>
      <>
        <div className='App'>
          <nav style={{display: "flex", gap: '30px'}}>
            <Link to="/">홈</Link>
            |
            <Link to="/sign-up">회원가입</Link>
          </nav>
          <Router/>
        </div>
      </>
    </BrowserRouter>
  )
}

export default App
