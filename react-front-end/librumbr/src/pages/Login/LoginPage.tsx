import { Button, Card, Form, Input, Typography, message } from 'antd';
import { useNavigate } from 'react-router-dom';

const { Title, Link } = Typography;

const LoginPage = () => {
  const navigate = useNavigate();
  const [messageApi, contextHolder] = message.useMessage(); // <-- aqui está certo agora

  const handleLogin = (values: any) => {
    console.log('Login enviado:', values);

    const loginValido = true;

    if (loginValido) {
      messageApi.success('Login realizado com sucesso!');
      navigate('/dashboard');
    } else {
      messageApi.error('E-mail ou senha inválidos');
    }
  };

  const irParaCadastro = () => {
    navigate('/cadastrar');
  };

  const esqueceuSenha = () => {
    messageApi.info('Função de recuperação de senha ainda não implementada');
  };

  return (
    <>
      {contextHolder} {/* importante para renderizar as mensagens */}
      <div className="login-container" style={{
        display: 'flex',
        justifyContent: 'center',
        alignItems: 'center',
        height: '100vh',
        backgroundColor: '#1e1e2f',
      }}>
        <Card
          title={<Title level={3} style={{ color: '#fff' }}>Login</Title>}
          style={{ width: 400, backgroundColor: '#2d2b55', borderColor: '#6C63FF' }}
          styles={{ header: { borderBottom: '1px solid #6C63FF' } }}
        >
          <Form layout="vertical" onFinish={handleLogin}>
            <Form.Item
              label="E-mail"
              name="email"
              rules={[{ required: true, message: 'Digite seu e-mail' }]}
            >
              <Input type="email" />
            </Form.Item>

            <Form.Item
              label="Senha"
              name="senha"
              rules={[{ required: true, message: 'Digite sua senha' }]}
            >
              <Input.Password />
            </Form.Item>

            <Form.Item>
              <Button type="primary" htmlType="submit" block>
                Entrar
              </Button>
            </Form.Item>

            <div style={{ display: 'flex', justifyContent: 'space-between' }}>
              <Link onClick={irParaCadastro}>Cadastrar-se</Link>
              <Link onClick={esqueceuSenha}>Esqueceu a senha?</Link>
            </div>
          </Form>
        </Card>
      </div>
    </>
  );
};

export default LoginPage;
