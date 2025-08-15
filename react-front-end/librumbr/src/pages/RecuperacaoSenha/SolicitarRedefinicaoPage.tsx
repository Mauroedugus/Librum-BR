import React, { useState } from 'react';
import { Form, Input, Button, Typography, Alert, Card } from 'antd';
import { MailOutlined } from '@ant-design/icons';

const containerStyle: React.CSSProperties = {
  display: 'flex',
  justifyContent: 'center',
  alignItems: 'center',
  height: '100vh',
  backgroundColor: '#f0f2f5',
};

const { Title, Paragraph } = Typography;

const SolicitarRedefinicaoPage: React.FC = () => {
  const [loading, setLoading] = useState(false);
  const [message, setMessage] = useState<string | null>(null);
  const [error, setError] = useState<string | null>(null);

  const onFinish = async (values: { email: string }) => {
    setLoading(true);
    setMessage(null);
    setError(null);

    try {
      const response = await fetch('http://localhost:8080/auth/forgot-password', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json',  },
        body: JSON.stringify({ email: values.email }),
      });

      if (response.ok) {
        setMessage('Se existir uma conta com este e-mail, um link de redefinição de senha foi enviado.');
      } else {
        // Por segurança, mostramos a mesma mensagem mesmo em caso de erro.
        setMessage('Se existir uma conta com este e-mail, um link de redefinição de senha foi enviado.');
      }
    } catch (err) {
      setError('Falha na comunicação com o servidor. Tente novamente mais tarde.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div style={containerStyle}>
      <Card style={{ width: 400 }}>
        <Title level={3} style={{ textAlign: 'center' }}>Esqueceu a senha?</Title>
        <Paragraph style={{ textAlign: 'center', marginBottom: 24 }}>
          Não se preocupe! Digite seu e-mail abaixo e enviaremos um link para você criar uma nova senha.
        </Paragraph>

        {message ? (
          <Alert message={message} type="success" showIcon />
        ) : (
          <Form onFinish={onFinish}>
            <Form.Item
              name="email"
              rules={[
                { required: true, message: 'Por favor, insira seu e-mail!' },
                { type: 'email', message: 'O e-mail inserido não é válido!' },
              ]}
            >
              <Input prefix={<MailOutlined />} placeholder="Seu e-mail de cadastro" />
            </Form.Item>

            {error && <Alert message={error} type="error" showIcon style={{ marginBottom: 24 }} />}

            <Form.Item>
              <Button type="primary" htmlType="submit" block loading={loading}>
                Enviar Link de Recuperação
              </Button>
            </Form.Item>
          </Form>
        )}
      </Card>
    </div>
  );
};

export default SolicitarRedefinicaoPage;