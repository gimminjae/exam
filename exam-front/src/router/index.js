import {Route, Routes} from 'react-router-dom'
import {RouterPages} from "./RouterPages";


const Home = () => <div>Home Page</div>

function RootRouter() {
  return (
    <Routes>
      <Route path="/" element={<Home/>}/>
      {
        RouterPages.map(page =>
          <Route path={page.path} element={page.page}/>
        )
      }
    </Routes>
  )
}

export default RootRouter
