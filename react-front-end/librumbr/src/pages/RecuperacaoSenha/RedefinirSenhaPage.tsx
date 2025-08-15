import React, { useState, useEffect } from 'react';
import { Form, Input, Button, Typography, Alert, Spin } from 'antd';
import { LockOutlined } from '@ant-design/icons';
import { useSearchParams } from 'react-router-dom';

const containerStyle: React.CSSProperties = {
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center',
    height: '100vh',
    backgroundColor: '#f0f2f5',
};

const boxStyle: React.CSSProperties = {
    width: '100%',
    maxWidth: '400px',
    padding: '40px',
    backgroundColor: 'white',
    borderRadius: '8px',
    boxShadow: '0 4px 12px rgba(0, 0, 0, 0.1)',
};

const { Title } = Typography;

const RedefinirSenhaPage: React.FC = () => {
    const [searchParams] = useSearchParams();
    const [token, setToken] = useState<string | null>(null);

    const [error, setError] = useState<string | null>(null);
    const [message, setMessage] = useState<string | null>(null);
    const [loading, setLoading] = useState(false);

    useEffect(() => {
        const tokenFromUrl = searchParams.get('token');
        if (tokenFromUrl) {
            setToken(tokenFromUrl);
        } else {
            setError('Token de redefinição não encontrado. Por favor, verifique o link do seu e-mail.');
        }
    }, [searchParams]);

    const onFinish = async (values: any) => {
        setLoading(true);
        setError(null);
        setMessage(null);

        const { password } = values;

        if (!token){
            setError('Token inválido. Não é possível redefinir a senha.');
            setLoading(false);
            return;
        }

        try {
            const response = await fetch('http://localhost:8080/auth/reset-password', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    token: token,
                    newPassword:password,
                }),
            })
            if (response.ok) {
                setMessage('Senha redefinida com sucesso! Você já pode fechar esta página e fazer o login.');
            } else {
                const errorData = await response.json();
                setError(errorData.detail || 'Ocorreu um erro. O token pode ser inválido ou ter expirado.');
            }
        } catch(err){
            setError('Falha na comunicação com o servidor. Verifique sua conexão e tente novamente.');
        } finally {
            setLoading(false);
        }
    };
    
    if (token === null && !error){
        return <Spin size="large" fullscreen />;
    }

    return (
        <div style={containerStyle}>
            <div style={boxStyle}>
                <Title level={2} style={{ textAlign: 'center', marginBottom: '24px', color: 'black' }}>
                    Redefinir Senha
                </Title>

                {message ? (
                    <Alert message={message} type="success" showIcon />
                ) : (
                    <Form
                        name="reset_password"
                        onFinish={onFinish}
                        autoComplete='off'
                    >
                        <Form.Item
                            name="password"
                            rules={[
                                { required: true, message: 'Por favor, insira sua nova senha!'}
                            ]}
                            hasFeedback
                        >
                            <Input.Password
                                prefix={<LockOutlined/>}
                                placeholder="Nova Senha"
                            />
                        </Form.Item>

                        <Form.Item
                            name="confirm"
                            dependencies={['password']}
                            hasFeedback
                            rules={[
                                { required: true, message: "Por favor, confirme sua senha!"},
                                ({ getFieldValue }) => ({
                                    validator(_, value) {
                                        if (!value || getFieldValue('password') === value) {
                                            return Promise.resolve();
                                        }
                                        return Promise.reject(new Error('As senhas não coincidem!'));
                                    },
                                }),
                            ]}
                        >
                            <Input.Password 
                                prefix={<LockOutlined />}
                                placeholder='Confirmar Nova Senha'
                            />
                        </Form.Item>

                            <Form.Item>
                                <Button type='primary' htmlType='submit' block loading={loading} disabled={!token}>
                                    Salvar Nova Senha
                                </Button>
                            </Form.Item>

                            {error && (
                                <Form.Item>
                                    <Alert message={error} type="error" showIcon />
                                </Form.Item>
                            )}
                    </Form>
                )}
            </div>
        </div>
    );
};

export default RedefinirSenhaPage;