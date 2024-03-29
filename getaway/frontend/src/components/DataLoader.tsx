import React from "react";
import styled from "styled-components";

interface LoadableDataProps {
    spinnerMultiplier?: number;
    isLoading: boolean;
    children: React.ReactNode;
}

const SpinnerRing = styled.div<{ sizeMultiplier: number }>`
  display: flex;
  justify-self: center;
  align-self: center;
  align-content: center;
  justify-content: center;
  width: ${({sizeMultiplier}) => `${80 * sizeMultiplier}px`};
  height: ${({sizeMultiplier}) => `${80 * sizeMultiplier}px`};

  div {
    box-sizing: border-box;
    display: block;
    position: absolute;
    width: ${({sizeMultiplier}) => `${64 * sizeMultiplier}px`};
    height: ${({sizeMultiplier}) => `${64 * sizeMultiplier}px`};
    margin: ${({sizeMultiplier}) => `${8 * sizeMultiplier}px`};
    border: ${(props) => `${8 * props.sizeMultiplier}px solid black`};
    border-radius: 50%;
    animation: lds-ring 1.2s cubic-bezier(0.5, 0, 0.5, 1) infinite;
    border-color: black transparent transparent transparent;
  }
  div:nth-child(1) {
    animation-delay: -0.45s;
  }
  nth-child(2) {
    animation-delay: -0.3s;
  }
  nth-child(3) {
    animation-delay: -0.15s;
  }
  @keyframes lds-ring {
    0% {
      transform: rotate(0deg);
    }
    100% {
      transform: rotate(360deg);
    }
  }
`;

export default function DataLoader({spinnerMultiplier = 1, isLoading, children,}: LoadableDataProps) {
    return (
        <>
            {isLoading ?
                <SpinnerRing sizeMultiplier={spinnerMultiplier} data-testid="spinner">
                    <div></div>
                    <div></div>
                    <div></div>
                    <div></div>
                </SpinnerRing>
                :
                <>{children}</>
            }
        </>
    );
}