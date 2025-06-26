import {
  Button,
  Form,
  Input,
  Typography,
  Select,
  message,
  Row,
  Col,
  Card,
} from 'antd';
import { useState } from 'react';

const { Title } = Typography;
const { Option } = Select;

const UsuarioCadastroPage = () => {
  const [form] = Form.useForm();
  const [salvando, setSalvando] = useState(false);
  const [messageApi, contextHolder] = message.useMessage();

  const handleSubmit = async (valores: any) => {
    if (valores.senha !== valores.confirmarSenha) {
      messageApi.error('As senhas não coincidem!');
      return;
    }

    setSalvando(true);
    setTimeout(() => {
      messageApi.success('Usuário cadastrado com sucesso!');
      console.log('Usuário mock:', valores);
      setSalvando(false);
      form.resetFields();
    }, 1000);
  };

  return (
    <>
      {contextHolder} 
      <div
        style={{
          display: 'flex',
          justifyContent: 'center',
          alignItems: 'center',
          backgroundColor: '#1e1e2f',
          minHeight: '100vh',
          padding: '16px',
        }}
      >
        <Card
          title={
            <Title level={3} style={{ marginBottom: 0, color: '#fff' }}>
              Cadastro de Usuário
            </Title>
          }
          style={{
            width: '100%',
            maxWidth: 500,
            backgroundColor: '#2d2b55',
            borderColor: '#6C63FF',
          }}
          styles={{
            header: { borderBottom: '1px solid #6C63FF', color: '#fff' },
            body: { color: '#fff' },
          }}
        >
          <Form layout="vertical" form={form} onFinish={handleSubmit} size="large">
            <Form.Item name="nome" label="Nome completo" rules={[{ required: true }]}>
              <Input />
            </Form.Item>

            <Form.Item name="email" label="E-mail" rules={[{ required: true, type: 'email' }]}>
              <Input />
            </Form.Item>

            <Row gutter={16}>
              <Col span={12}>
                <Form.Item name="senha" label="Senha" rules={[{ required: true, min: 6 }]}>
                  <Input.Password />
                </Form.Item>
              </Col>
              <Col span={12}>
                <Form.Item name="confirmarSenha" label="Confirmar Senha" rules={[{ required: true }]}>
                  <Input.Password />
                </Form.Item>
              </Col>
            </Row>

            <Form.Item name="tipo" label="Tipo de Usuário" rules={[{ required: true }]}>
              <Select placeholder="Selecione o tipo">
                <Option value="bibliotecario">Bibliotecário</Option>
                <Option value="atendente">Leitor</Option>
              </Select>
            </Form.Item>

            <Form.Item>
              <Button type="primary" htmlType="submit" loading={salvando} block>
                Cadastrar Usuário
              </Button>
            </Form.Item>
          </Form>
        </Card>
      </div>
    </>
  );
};

export default UsuarioCadastroPage;
