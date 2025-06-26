import { Button, Card } from 'antd';
import { useNavigate } from 'react-router-dom';
import logoCompleta from '../../assets/logo-completa.png';

const InicioPage = () => {
  const navigate = useNavigate();

  return (
    <div
      style={{
        backgroundColor: '#1e1e2f',
        height: '100vh',
        display: 'flex',
        justifyContent: 'center',
        alignItems: 'center',
        padding: '16px',
      }}
    >
      <Card
        style={{
          width: 400,
          backgroundColor: '#2d2b55',
          textAlign: 'center',
          borderColor: '#6C63FF',
        }}
        styles={{
          header: { borderBottom: '1px solid #6C63FF' },
        }}
      >
        
        <img
          src={logoCompleta}
          alt="Logo LibrumBR"
          style={{
            height: 200,
            marginBottom: 10,
            objectFit: 'contain',
          }}
        />

        <Button
          type="primary"
          block
          size="large"
          onClick={() => navigate('/login')}
          style={{ marginBottom: 16 }}
        >
          Entrar
        </Button>

        <Button
          block
          size="large"
          onClick={() => navigate('/cadastrar')}
        >
          Cadastrar-se
        </Button>
      </Card>
    </div>
  );
};

export default InicioPage;
