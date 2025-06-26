import { BrowserRouter, Routes, Route } from 'react-router-dom';
import LoginPage from './pages/Login/LoginPage';
import UsuarioCadastroPage from './pages/CadastroUsuario/UsuarioCadastroPage';
import DashboardPage from './pages/Dashboard/DashboardPage';
import LivroCadastroPage from './pages/Livros/LivroCadastroPage';
import ExemplarCadastroPage from './pages/Exemplares/ExemplarCadastroPage';
import LeitoresPage from './pages/Leitores/LeitoresPage';
import MainLayout from './layout/MainLayout';
import InicioPage from './pages/Inicio/InicioPage';

const App = () => (
  <BrowserRouter>
    <Routes>
      <Route path="/" element={<InicioPage  />} />
      <Route path="/login" element={<LoginPage />} />
      <Route path="/cadastrar" element={<UsuarioCadastroPage />} />
      <Route path="/" element={<MainLayout />}>
        <Route path="dashboard" element={<DashboardPage />} />
        <Route path="livros/cadastrar" element={<LivroCadastroPage />} />
        <Route path="exemplares/cadastrar" element={<ExemplarCadastroPage />} />
        <Route path="leitores" element={<LeitoresPage />} />
      </Route>
    </Routes>
  </BrowserRouter>
);

export default App;
