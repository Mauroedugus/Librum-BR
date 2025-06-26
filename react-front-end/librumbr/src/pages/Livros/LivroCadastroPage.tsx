import {
  Button,
  Form,
  Input,
  InputNumber,
  Typography,
  Upload,
  message,
  Row,
  Col,
  Spin,
} from 'antd';
import { UploadOutlined } from '@ant-design/icons';
import { useState } from 'react';
import type { UploadRequestOption } from 'rc-upload/lib/interface';
import './LivroCadastroPage.css';
import { useContext } from 'react';
import { ThemeContext } from '../../layout/ThemeContext';


const { Title, Text } = Typography;

const LivroCadastroPage = () => {
  const [form] = Form.useForm();
  const [carregandoISBN, setCarregandoISBN] = useState(false);
  const [imagemPreview, setImagemPreview] = useState<string | null>(null);
  const [imagemArquivo, setImagemArquivo] = useState<File | null>(null);
  const [messageApi, contextHolder] = message.useMessage();
  const { darkTheme } = useContext(ThemeContext);


  const buscarPorISBN = async (isbn: string) => {
    if (!isbn || isbn.length < 10) {
      messageApi.warning('Digite um ISBN válido com pelo menos 10 caracteres.');
      return;
    }

    try {
      setCarregandoISBN(true);

      const response = await fetch(`https://brasilapi.com.br/api/isbn/v1/${isbn}?providers=open-library,google-books`);
      if (!response.ok) throw new Error('Livro não encontrado');
      const data = await response.json();

      form.setFieldsValue({
        titulo: data.title,
        autor: data.authors?.join(', '),
        editora: data.publisher,
      });

      if (data.cover_url) {
        setImagemPreview(data.cover_url);
        setImagemArquivo(null); 
      } else {
        setImagemPreview(null);
      }

      messageApi.success('Livro encontrado e preenchido com sucesso!');
    } catch (err) {
      setImagemPreview(null);
      setImagemArquivo(null);
      messageApi.warning('⚠️ Livro não encontrado na BrasilAPI. Preencha manualmente.');
    } finally {
      setCarregandoISBN(false);
    }
  };

  const handleCustomUpload = (options: UploadRequestOption<any>) => {
    const { file, onSuccess, onError } = options;

    if (!(file instanceof File)) {
      messageApi.error('Arquivo inválido.');
      onError?.(new Error('Arquivo inválido'));
      return;
    }

    if (!file.type.startsWith('image/')) {
      messageApi.error('Por favor, selecione uma imagem válida.');
      onError?.(new Error('Tipo inválido'));
      return;
    }

    const reader = new FileReader();

    reader.onload = () => {
      if (typeof reader.result === 'string') {
        setImagemPreview(reader.result);
        setImagemArquivo(file);
        onSuccess?.("ok");
      }
    };

    reader.onerror = () => {
      messageApi.error('Erro ao gerar preview da imagem.');
      onError?.(new Error('Erro ao ler imagem'));
    };

    reader.readAsDataURL(file);
  };

  const handleSubmit = (values: any) => {
    console.log('Dados do formulário:', values);
    if (imagemArquivo) {
      console.log('Imagem enviada manualmente:', imagemArquivo);
    } else {
      console.log('Imagem de capa vinda da API:', imagemPreview);
    }

    messageApi.success('Livro salvo com sucesso!');
  };

  return (
    <>
      {contextHolder}
      <div className={`livro-cadastro-container ${darkTheme ? 'dark' : 'light'}`}>
        <Title level={2} className="livro-cadastro-title">
          Cadastro de Livro
        </Title>

        <Form
          layout="vertical"
          form={form}
          onFinish={handleSubmit}
          className="livro-cadastro-form"
          style={{ maxWidth: 900 }}
        >
          <Row gutter={16}>
            <Col span={18}>
              <Form.Item label="ISBN" name="isbn">
                <Spin spinning={carregandoISBN}>
                  <Input
                    placeholder="Digite o ISBN e pressione TAB"
                    onBlur={(e) => buscarPorISBN(e.target.value)}
                    className="livro-cadastro-input"
                  />
                </Spin>
              </Form.Item>
            </Col>
            <Col span={6}>
              <Form.Item label="Ano" name="ano">
                <InputNumber
                  min={1000}
                  max={9999}
                  className="livro-cadastro-inputnumber"
                />
              </Form.Item>
            </Col>
          </Row>

          <Row gutter={16}>
            <Col span={12}>
              <Form.Item label="Título" name="titulo" rules={[{ required: true }]}>
                <Input className="livro-cadastro-input" />
              </Form.Item>
              <Form.Item label="Autor(es)" name="autor" rules={[{ required: true }]}>
                <Input className="livro-cadastro-input" />
              </Form.Item>
              <Form.Item label="Categoria" name="categoria">
                <Input className="livro-cadastro-input" />
              </Form.Item>
            </Col>

            <Col span={12}>
              <Form.Item label="Editora" name="editora">
                <Input className="livro-cadastro-input" />
              </Form.Item>
              <Form.Item label="Nº de Páginas" name="paginas">
                <InputNumber min={1} className="livro-cadastro-inputnumber" />
              </Form.Item>
              <Form.Item label="Exemplares" name="quantidade">
                <InputNumber min={1} className="livro-cadastro-inputnumber" />
              </Form.Item>
            </Col>
          </Row>

          <Form.Item label="Sinopse" name="sinopse">
            <Input.TextArea rows={4} className="livro-cadastro-textarea" />
          </Form.Item>

          <Form.Item style={{ marginTop: 32 }}>
            <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
              <Text strong className="livro-cadastro-capa-text" style={{ marginBottom: 12 }}>
                Capa do Livro
              </Text>

              {imagemPreview && (
                <img
                  src={imagemPreview}
                  alt="Prévia da Capa"
                  className="livro-cadastro-image-preview"
                />
              )}

              <Upload
                customRequest={handleCustomUpload}
                showUploadList={false}
                accept="image/*"
                maxCount={1}
              >
                <Button icon={<UploadOutlined />} className="livro-cadastro-upload-btn">
                  Selecionar Imagem
                </Button>
              </Upload>
            </div>
          </Form.Item>

          <Form.Item>
            <Button type="primary" htmlType="submit">
              Salvar Livro
            </Button>
          </Form.Item>
        </Form>
      </div>
    </>
  );
};

export default LivroCadastroPage;
