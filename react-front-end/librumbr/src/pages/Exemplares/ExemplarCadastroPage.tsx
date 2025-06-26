import {
  Button,
  Form,
  Input,
  InputNumber,
  Typography,
  message,
  Row,
  Col,
  Card,
  Spin,
} from 'antd';
import { useState, useContext } from 'react';
import { ThemeContext } from '../../layout/ThemeContext';
import './ExemplarCadastroPage.css';

const { Title, Text } = Typography;

const ExemplarCadastroPage = () => {
  const { darkTheme } = useContext(ThemeContext);
  const [form] = Form.useForm();
  const [carregando, setCarregando] = useState(false);
  const [livro, setLivro] = useState<any | null>(null);
  const [messageApi, contextHolder] = message.useMessage();

  const buscarLivro = async (isbn: string) => {
    if (!isbn || isbn.length < 10) {
      messageApi.warning('Digite um ISBN válido.');
      return;
    }

    try {
      setCarregando(true);
      const res = await fetch(`https://brasilapi.com.br/api/isbn/v1/${isbn}?providers=open-library,google-books`);
      if (!res.ok) throw new Error();
      const data = await res.json();
      setLivro(data);
      messageApi.success('Livro encontrado com sucesso!');
    } catch {
      setLivro(null);
      messageApi.warning('Livro não encontrado. Verifique o ISBN.');
    } finally {
      setCarregando(false);
    }
  };

  const handleSubmit = (values: any) => {
    console.log('Cadastro de Exemplares:', {
      isbn: values.isbn,
      quantidade: values.quantidade,
      livro,
    });

    messageApi.success('Exemplares cadastrados com sucesso!');
  };

  return (
    <>
      {contextHolder}
      <div className={`exemplar-cadastro-container ${darkTheme ? 'dark' : 'light'}`}>
        <Title level={2} className="exemplar-cadastro-title">Cadastro de Exemplares</Title>

        <Form
          layout="vertical"
          form={form}
          onFinish={handleSubmit}
          style={{ maxWidth: 800 }}
        >
          <Row gutter={16}>
            <Col span={18}>
              <Form.Item label="ISBN" name="isbn" rules={[{ required: true }]}>
                <Input
                  placeholder="Digite o ISBN e pressione TAB"
                  onBlur={(e) => buscarLivro(e.target.value)}
                />
              </Form.Item>
            </Col>
            <Col span={6}>
              <Form.Item label="Quantidade" name="quantidade" rules={[{ required: true }]}>
                <InputNumber min={1} style={{ width: '100%' }} />
              </Form.Item>
            </Col>
          </Row>

          {livro && (
            <Card
              className="exemplar-cadastro-card"
            >
              <Row gutter={16}>
                <Col span={4}>
                  <img
                    src={livro.cover_url || 'https://via.placeholder.com/100x150'}
                    alt="Capa do Livro"
                    style={{ width: '100%', borderRadius: 4 }}
                  />
                </Col>
                <Col span={20}>
                  <Text strong>Título:</Text> <Text>{livro.title}</Text><br />
                  <Text strong>Autor(es):</Text> <Text>{livro.authors?.join(', ')}</Text><br />
                  <Text strong>Editora:</Text> <Text>{livro.publisher}</Text>
                </Col>
              </Row>
            </Card>
          )}

          <Form.Item style={{ marginTop: 24 }}>
            <Button type="primary" htmlType="submit" disabled={!livro}>
              Salvar Exemplares
            </Button>
          </Form.Item>
        </Form>

        {carregando && (
          <Spin
            spinning={carregando}
            tip="Buscando informações do livro..."
            style={{ marginTop: 16 }}
          >
            <div style={{ height: 100 }} />
          </Spin>
        )}
      </div>
    </>
  );
};

export default ExemplarCadastroPage;