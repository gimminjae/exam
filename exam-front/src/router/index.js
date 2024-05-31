import {Route, Routes} from 'react-router-dom'
import SignupPage from "../pages/member/SignupPage";


const Home = () => <div>Home Page</div>

function RootRouter() {
  return (
    <Routes>
      <Route path="/" exact element={<Home/>}/>
      <Route path="/sign-up" element={<SignupPage/>}/>
    </Routes>
  )
}

export default RootRouter
