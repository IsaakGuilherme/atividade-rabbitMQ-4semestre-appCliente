FROM python:3.12-slim

WORKDIR /app

# Garante que os prints do Python apareçam em tempo real nos logs do Docker
ENV PYTHONUNBUFFERED=1

COPY requirements.txt* ./
RUN if [ -f requirements.txt ]; then pip install --no-cache-dir -r requirements.txt; else pip install --no-cache-dir pika requests; fi

COPY . .

CMD ["python", "main.py"]